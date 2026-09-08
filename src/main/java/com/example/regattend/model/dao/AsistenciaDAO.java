package com.example.regattend.model.dao;

import com.example.regattend.config.DatabaseConnection;
import com.example.regattend.model.entity.Asistencia;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

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

                String fechaStr = rs.getString("fecha");
                if (fechaStr != null && !fechaStr.isEmpty()) {
                    a.setFecha(LocalDate.parse(fechaStr));
                }

                String horaEntradaStr = rs.getString("hora_entrada");
                if (horaEntradaStr != null && !horaEntradaStr.isEmpty()) {
                    a.setHoraEntrada(LocalTime.parse(horaEntradaStr));
                }

                String horaSalidaStr = rs.getString("hora_salida");
                if (horaSalidaStr != null && !horaSalidaStr.isEmpty()) {
                    a.setHoraSalida(LocalTime.parse(horaSalidaStr));
                }

                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertarEntrada(int usuarioId, LocalDate fecha, LocalTime horaEntrada) {
        String sql = "INSERT INTO asistencias (usuario_id, fecha, hora_entrada) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usuarioId);
            pstmt.setString(2, fecha.toString());
            pstmt.setString(3, horaEntrada.toString());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarSalida(int usuarioId, LocalDate fecha, LocalTime horaSalida) {
        String sql = "UPDATE asistencias SET hora_salida = ? WHERE usuario_id = ? AND fecha = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, horaSalida.toString());
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