package com.example.regattend.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:regattend.db";
    private static boolean dbInicializada = false; // Bandera para ejecutar la creación solo una vez

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);

        // 1. Habilitar obligatoriamente las Claves Foráneas (Foreign Keys) en SQLite
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }

        // 2. Crear estructura si no existe (solo la primera vez que se invoca la conexión en la app)
        if (!dbInicializada) {
            inicializarEstructura(conn);
            dbInicializada = true;
        }

        return conn;
    }

    private static void inicializarEstructura(Connection conn) {
        String tablaUsuarios = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "correo TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "nombre TEXT NOT NULL, " +
                "rol TEXT NOT NULL, " +
                "activo INTEGER NOT NULL DEFAULT 1" +
                ");";

        String tablaAsistencias = "CREATE TABLE IF NOT EXISTS asistencias (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER NOT NULL, " +
                "accion TEXT NOT NULL, " +
                "fecha_hora DATETIME NOT NULL, " +
                "FOREIGN KEY(usuario_id) REFERENCES usuarios(id)" +
                ");";

        // Inserta al admin solo si no existe el ID 1 (gracias a INSERT OR IGNORE)
        String adminDefault = "INSERT OR IGNORE INTO usuarios (id, correo, password, nombre, rol, activo) " +
                "VALUES (1, 'admin@regattend.cl', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrador Principal', 'ADMIN', 1);";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(tablaUsuarios);
            stmt.execute(tablaAsistencias);
            stmt.execute(adminDefault);
        } catch (SQLException e) {
            System.err.println("Error al inicializar las tablas de la BD: " + e.getMessage());
        }
    }
}