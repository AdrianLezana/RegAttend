package com.example.regattend.model.dao;

import com.example.regattend.config.DatabaseConnection;
import com.example.regattend.model.entity.Asistencia;

import java.sql.*;
import java.time.LocalDate;

public class AsistenciaDAO {

    public Asistencia buscarPorUsuarioYFecha(int usuarioId, LocalDate fecha) {
        String sql = "SELECT * FROM asistencias WHERE usuario_id = ? AND fecha = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            pstmt.setString(2, fecha.toString());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Asistencia a = new Asistencia();
                a.setId(rs.getInt("id"));
                a.setUsuarioId(rs.getInt("usuario_id"));
                a.setFecha(rs.getString("fecha"));
                a.setHoraEntrada(rs.getString("hora_entrada"));
                a.setHoraSalida(rs.getString("hora_salida"));
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertarEntrada(int usuarioId, LocalDate fecha, String horaEntrada) {
        String sql = "INSERT INTO asistencias (usuario_id, fecha, hora_entrada) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            pstmt.setString(2, fecha.toString());
            pstmt.setString(3, horaEntrada);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarSalida(int usuarioId, LocalDate fecha, String horaSalida) {
        String sql = "UPDATE asistencias SET hora_salida = ? WHERE usuario_id = ? AND fecha = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, horaSalida);
            pstmt.setInt(2, usuarioId);
            pstmt.setString(3, fecha.toString());
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}