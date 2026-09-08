package com.example.regattend.model.dao;

import com.example.regattend.config.DatabaseConnection;
import com.example.regattend.model.entity.Asistencia;
import com.example.regattend.model.entity.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO implements IAsistenciaDAO {

    @Override
    public boolean registrarEntrada(int usuarioId, LocalDate fecha, LocalTime horaEntrada) {
        String sql = "INSERT INTO asistencias (usuario_id, fecha, hora_entrada) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, fecha.toString());
            stmt.setString(3, horaEntrada.toString());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean registrarSalida(int usuarioId, LocalDate fecha, LocalTime horaSalida) {
        String sql = "UPDATE asistencias SET hora_salida = ? WHERE usuario_id = ? AND fecha = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, horaSalida.toString());
            stmt.setInt(2, usuarioId);
            stmt.setString(3, fecha.toString());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Asistencia obtenerPorUsuarioYFecha(int usuarioId, LocalDate fecha) {
        String sql = "SELECT * FROM asistencias WHERE usuario_id = ? AND fecha = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, fecha.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Asistencia a = new Asistencia();
                    a.setId(rs.getInt("id"));
                    a.setUsuarioId(rs.getInt("usuario_id"));
                    a.setFecha(LocalDate.parse(rs.getString("fecha")));

                    String entradaStr = rs.getString("hora_entrada");
                    if (entradaStr != null) a.setHoraEntrada(LocalTime.parse(entradaStr));

                    String salidaStr = rs.getString("hora_salida");
                    if (salidaStr != null) a.setHoraSalida(LocalTime.parse(salidaStr));

                    return a;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Usuario> listarInasistencias(LocalDate fecha) {
        List<Usuario> inasistentes = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE activo = 1 AND id NOT IN " +
                "(SELECT usuario_id FROM asistencias WHERE fecha = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fecha.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setCorreo(rs.getString("correo"));
                    u.setRol(rs.getString("rol"));
                    u.setActivo(true);
                    inasistentes.add(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inasistentes;
    }

    @Override
    public List<Asistencia> listarAtrasos(LocalDate fechaLimite, LocalTime limiteAtraso) {
        List<Asistencia> atrasos = new ArrayList<>();
        String sql = "SELECT * FROM asistencias WHERE fecha = ? AND hora_entrada > ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fechaLimite.toString());
            stmt.setString(2, limiteAtraso.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Asistencia a = new Asistencia();
                    a.setId(rs.getInt("id"));
                    a.setUsuarioId(rs.getInt("usuario_id"));
                    a.setFecha(LocalDate.parse(rs.getString("fecha")));
                    a.setHoraEntrada(LocalTime.parse(rs.getString("hora_entrada")));
                    if (rs.getString("hora_salida") != null) {
                        a.setHoraSalida(LocalTime.parse(rs.getString("hora_salida")));
                    }
                    atrasos.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atrasos;
    }

    @Override
    public List<Asistencia> listarSalidasAnticipadas(LocalDate fechaLimite, LocalTime limiteSalida) {
        List<Asistencia> salidas = new ArrayList<>();
        String sql = "SELECT * FROM asistencias WHERE fecha = ? AND hora_salida < ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fechaLimite.toString());
            stmt.setString(2, limiteSalida.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Asistencia a = new Asistencia();
                    a.setId(rs.getInt("id"));
                    a.setUsuarioId(rs.getInt("usuario_id"));
                    a.setFecha(LocalDate.parse(rs.getString("fecha")));
                    if (rs.getString("hora_entrada") != null) {
                        a.setHoraEntrada(LocalTime.parse(rs.getString("hora_entrada")));
                    }
                    a.setHoraSalida(LocalTime.parse(rs.getString("hora_salida")));
                    salidas.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salidas;
    }
}