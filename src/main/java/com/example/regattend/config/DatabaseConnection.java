package com.example.regattend.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestiona la conexión a la base de datos SQLite.
 * Si el archivo no existe, lo crea automáticamente.
 */
public class DatabaseConnection {
    // La base de datos se guardará en la carpeta del proyecto
    private static final String URL = "jdbc:sqlite:regattend.db";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            initDatabase(conn);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conn;
    }

    /**
     * Crea las tablas iniciales si no existen y el usuario admin.
     */
    private static void initDatabase(Connection conn) {
        String createUsuarios = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "correo TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "nombre TEXT NOT NULL, " +
                "rol TEXT NOT NULL" +
                ");";

        String createAsistencias = "CREATE TABLE IF NOT EXISTS asistencias (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER NOT NULL, " +
                "tipo TEXT NOT NULL, " +
                "fecha_hora DATETIME NOT NULL, " +
                "FOREIGN KEY(usuario_id) REFERENCES usuarios(id)" +
                ");";

        String insertAdmin = "INSERT OR IGNORE INTO usuarios (id, correo, password, nombre, rol) " +
                "VALUES (1, 'admin@regattend.cl', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrador Principal', 'ADMIN');";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createUsuarios);
            stmt.execute(createAsistencias);
            stmt.execute(insertAdmin);
        } catch (SQLException e) {
            System.out.println("Error al inicializar las tablas: " + e.getMessage());
        }
    }
}
