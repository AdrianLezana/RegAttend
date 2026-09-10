package com.example.regattend.model.dao;
import com.example.regattend.config.DatabaseConnection;
import com.example.regattend.model.entity.ReporteItem;

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

    /**
     * Verifica si el usuario ya registró una acción (ENTRADA o SALIDA) en la fecha actual.
     */
    public boolean yaMarcoHoy(int usuarioId, String accion) {
        // Obtenemos la fecha de hoy en formato YYYY-MM-DD
        String fechaHoy = java.time.LocalDate.now().toString();

        // Usamos LIKE para buscar cualquier hora dentro de esa fecha
        String sql = "SELECT COUNT(*) FROM asistencias WHERE usuario_id = ? AND accion = ? AND fecha_hora LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            pstmt.setString(2, accion);
            pstmt.setString(3, fechaHoy + "%");

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ReporteItem> obtenerReporteAtrasos() {
        List<ReporteItem> reporte = new ArrayList<>();
        String sql = "SELECT u.nombre, date(a.fecha_hora) as fecha, strftime('%H:%M:%S', a.fecha_hora) as hora " +
                "FROM asistencias a JOIN usuarios u ON a.usuario_id = u.id " +
                "WHERE a.accion = 'ENTRADA' AND strftime('%H:%M:%S', a.fecha_hora) > '09:30:00'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reporte.add(new ReporteItem(rs.getString("nombre"), rs.getString("fecha"), rs.getString("hora"), "Atraso (>09:30)"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return reporte;
    }

    public List<ReporteItem> obtenerReporteSalidasAnticipadas() {
        List<ReporteItem> reporte = new ArrayList<>();
        String sql = "SELECT u.nombre, date(a.fecha_hora) as fecha, strftime('%H:%M:%S', a.fecha_hora) as hora " +
                "FROM asistencias a JOIN usuarios u ON a.usuario_id = u.id " +
                "WHERE a.accion = 'SALIDA' AND strftime('%H:%M:%S', a.fecha_hora) < '17:30:00'";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                reporte.add(new ReporteItem(rs.getString("nombre"), rs.getString("fecha"), rs.getString("hora"), "Salida Anticipada"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return reporte;
    }

    public List<ReporteItem> obtenerReporteInasistencias(LocalDate fecha) {
        List<ReporteItem> reporte = new ArrayList<>();
        String sql = "SELECT nombre FROM usuarios WHERE activo = 1 AND rol = 'EMPLEADO' AND id NOT IN (" +
                "SELECT DISTINCT usuario_id FROM asistencias WHERE date(fecha_hora) = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, fecha.toString());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reporte.add(new ReporteItem(rs.getString("nombre"), fecha.toString(), "--:--", "Inasistencia"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return reporte;
    }
}