package com.example.regattend.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

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
                "rol TEXT NOT NULL, " +
                "activo INTEGER NOT NULL DEFAULT 1" +
                ");";

        String createAsistencias = "CREATE TABLE IF NOT EXISTS asistencias (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario_id INTEGER NOT NULL, " +
                "tipo TEXT NOT NULL, " +
                "fecha_hora DATETIME NOT NULL, " +
                "FOREIGN KEY(usuario_id) REFERENCES usuarios(id)" +
                ");";

        String insertAdmin = "INSERT OR IGNORE INTO usuarios (id, correo, password, nombre, rol, activo) " +
                "VALUES (1, 'admin@regattend.cl', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrador Principal', 'ADMIN', 1);";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createUsuarios);
            ensureActivoColumn(conn);
            stmt.execute(createAsistencias);
            stmt.execute(insertAdmin);
        } catch (SQLException e) {
            System.out.println("Error al inicializar las tablas: " + e.getMessage());
        }
    }

    /**
     * Añade la columna de borrado lógico a bases de datos creadas con versiones anteriores.
     */
    private static void ensureActivoColumn(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("PRAGMA table_info(usuarios)")) {
            while (rs.next()) {
                if ("activo".equalsIgnoreCase(rs.getString("name"))) {
                    return;
                }
            }
        }

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("ALTER TABLE usuarios ADD COLUMN activo INTEGER NOT NULL DEFAULT 1");
        }
    }
}
