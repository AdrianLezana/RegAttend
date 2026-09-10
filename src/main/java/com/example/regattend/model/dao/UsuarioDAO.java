package com.example.regattend.model.dao;

import com.example.regattend.config.DatabaseConnection;
import com.example.regattend.model.entity.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario validarLogin(String correo, String password) {
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND password = ? AND activo = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, correo);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("correo"), rs.getString("rol"), rs.getBoolean("activo"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Usuario> listarUsuariosActivos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE activo = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("correo"), rs.getString("rol"), true));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public boolean crearUsuario(String nombre, String correo, String password, String rol) {
        String sql = "INSERT INTO usuarios (nombre, correo, password, rol, activo) VALUES (?, ?, ?, ?, 1)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, correo);
            pstmt.setString(3, password);
            pstmt.setString(4, rol);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public boolean actualizarUsuario(int id, String nombre, String correo, String rol) {
        String sql = "UPDATE usuarios SET nombre = ?, correo = ?, rol = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, correo);
            pstmt.setString(3, rol);
            pstmt.setInt(4, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public boolean eliminarUsuario(int idUsuario) {
        String sql = "UPDATE usuarios SET activo = 0 WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idUsuario);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }
}