package com.example.regattend.model.dao;

import com.example.regattend.config.DatabaseConnection;
import com.example.regattend.model.entity.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para gestionar los usuarios en la BD.
 */
public class UsuarioDAO {

    public List<Usuario> getAllUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT id, correo, password, nombre, rol, activo FROM usuarios WHERE activo = 1";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("correo"),
                        rs.getString("password"),
                        rs.getString("nombre"),
                        rs.getString("rol"),
                        rs.getBoolean("activo")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo usuarios: " + e.getMessage());
        }
        return lista;
    }

    public boolean createUsuario(Usuario u) {
        String sql = "INSERT INTO usuarios (correo, password, nombre, rol, activo) VALUES (?, ?, ?, ?, 1)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, u.getCorreo());
            pstmt.setString(2, u.getPassword()); // Debe venir hasheada
            pstmt.setString(3, u.getNombre());
            pstmt.setString(4, u.getRol());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error creando usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUsuario(Usuario u) {
        // Actualizamos todos los datos. Si la password está vacía, no deberíamos actualizarla, pero para simplificar lo requeriremos todo.
        String sql = "UPDATE usuarios SET correo = ?, password = ?, nombre = ?, rol = ? WHERE id = ? AND activo = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, u.getCorreo());
            pstmt.setString(2, u.getPassword());
            pstmt.setString(3, u.getNombre());
            pstmt.setString(4, u.getRol());
            pstmt.setInt(5, u.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizando usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUsuario(int id) {
        String sql = "UPDATE usuarios SET activo = 0 WHERE id = ? AND activo = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error desactivando usuario: " + e.getMessage());
            return false;
        }
    }

    public Usuario getByCorreo(String correo) {
        String sql = "SELECT id, correo, password, nombre, rol, activo FROM usuarios WHERE correo = ? AND activo = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("correo"),
                        rs.getString("password"),
                        rs.getString("nombre"),
                        rs.getString("rol"),
                        rs.getBoolean("activo")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error obteniendo usuario por correo: " + e.getMessage());
        }
        return null;
    }
}
