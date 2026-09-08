package com.example.regattend.config;

import com.example.regattend.util.PasswordHasher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:regattend.db";
    private static Connection instance = null;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (instance == null || instance.isClosed()) {
            instance = DriverManager.getConnection(URL);
            inicializarBaseDeDatos(instance);
        }
        return instance;
    }

    private static void inicializarBaseDeDatos(Connection conn) {
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "correo TEXT NOT NULL UNIQUE, " +
                "password_hash TEXT NOT NULL, " +
                "rol TEXT NOT NULL DEFAULT 'EMPLEADO', " +
                "activo INTEGER NOT NULL DEFAULT 1, " +
                "fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";

        String sqlAsistencias = "CREATE TABLE IF NOT EXISTS asistencias (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER NOT NULL, " +
                "fecha TEXT NOT NULL, " +
                "hora_entrada TEXT NULL, " +
                "hora_salida TEXT NULL, " +
                "CONSTRAINT fk_asistencia_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id), " +
                "CONSTRAINT uq_usuario_fecha UNIQUE (usuario_id, fecha));";

        // Hash precalculado para 'admin123' con SHA-256
        String adminPasswordHash = PasswordHasher.hashPassword("admin123");
        String sqlAdminDefault = "INSERT OR IGNORE INTO usuarios (id, nombre, correo, password_hash, rol, activo) " +
                "VALUES (1, 'Administrador General', 'admin@regattend.com', '" + adminPasswordHash + "', 'ADMIN', 1);";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUsuarios);
            stmt.execute(sqlAsistencias);
            stmt.execute(sqlAdminDefault);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}