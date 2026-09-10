package com.example.regattend.model.dao;
import com.example.regattend.config.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO {

    public boolean registrarAsistencia(int usuarioId, String accion) {
        String sql = "INSERT INTO asistencias (usuario_id, accion, fecha_hora) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            pstmt.setString(2, accion);
            // Guardamos la fecha en formato compatible con SQLite
            pstmt.setString(3, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public List<String> obtenerReporteAtrasos() {
        List<String> reporte = new ArrayList<>();
        // strftime extrae la hora en SQLite
        String sql = "SELECT u.nombre, date(a.fecha_hora) as fecha, strftime('%H:%M:%S', a.fecha_hora) as hora " +
                "FROM asistencias a JOIN usuarios u ON a.usuario_id = u.id " +
                "WHERE a.accion = 'ENTRADA' AND strftime('%H:%M:%S', a.fecha_hora) > '09:30:00'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) { reporte.add(rs.getString("nombre") + " - Día: " + rs.getString("fecha") + " - Hora: " + rs.getString("hora")); }
        } catch (SQLException e) { e.printStackTrace(); }
        return reporte;
    }

    public List<String> obtenerReporteSalidasAnticipadas() {
        List<String> reporte = new ArrayList<>();
        String sql = "SELECT u.nombre, date(a.fecha_hora) as fecha, strftime('%H:%M:%S', a.fecha_hora) as hora " +
                "FROM asistencias a JOIN usuarios u ON a.usuario_id = u.id " +
                "WHERE a.accion = 'SALIDA' AND strftime('%H:%M:%S', a.fecha_hora) < '17:30:00'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) { reporte.add(rs.getString("nombre") + " - Día: " + rs.getString("fecha") + " - Hora: " + rs.getString("hora")); }
        } catch (SQLException e) { e.printStackTrace(); }
        return reporte;
    }

    public List<String> obtenerReporteInasistencias(LocalDate fecha) {
        List<String> reporte = new ArrayList<>();
        String sql = "SELECT nombre FROM usuarios WHERE activo = 1 AND rol = 'EMPLEADO' AND id NOT IN (" +
                "SELECT DISTINCT usuario_id FROM asistencias WHERE date(fecha_hora) = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fecha.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) { reporte.add("Inasistencia: " + rs.getString("nombre")); }
        } catch (SQLException e) { e.printStackTrace(); }
        return reporte;
    }
}