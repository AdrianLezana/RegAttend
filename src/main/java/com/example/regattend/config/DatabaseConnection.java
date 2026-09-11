package com.example.regattend.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:regattend.db";
    private static boolean dbInicializada = false;

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }

        if (!dbInicializada) {
            inicializarEstructura(conn);
            dbInicializada = true;
        }

        return conn;
    }

    private static void inicializarEstructura(Connection conn) {
        String tablaUsuarios = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                correo TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                nombre TEXT NOT NULL,
                rol TEXT NOT NULL,
                activo INTEGER NOT NULL DEFAULT 1
            );
        """;

        String tablaAsistencias = """
            CREATE TABLE IF NOT EXISTS asistencias (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario_id INTEGER NOT NULL,
                accion TEXT NOT NULL,
                fecha_hora DATETIME NOT NULL,
                FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
            );
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(tablaUsuarios);
            stmt.execute(tablaAsistencias);

            // Insertar admin por defecto
            stmt.execute("INSERT OR IGNORE INTO usuarios (id, correo, password, nombre, rol, activo) " +
                    "VALUES (1, 'admin@regattend.cl', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrador Principal', 'ADMIN', 1);");

            // Insertar datos de prueba para el profesor
            insertarDatosDePrueba(stmt);

        } catch (SQLException e) {
            System.err.println("Error al inicializar las tablas de la BD: " + e.getMessage());
        }
    }

    private static void insertarDatosDePrueba(Statement stmt) throws SQLException {
        // Obtenemos la fecha del día en que el profesor ejecuta el programa
        String fechaHoy = LocalDate.now().toString();
        String hashAdmin = "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9";

        // Usamos INSERT OR IGNORE protegiendo por ID.
        // Si el profesor cierra y abre el programa, no se duplicarán los datos.
        String usuariosTest = """
            INSERT OR IGNORE INTO usuarios (id, correo, password, nombre, rol, activo) VALUES 
            (2, 'j.perez@regattend.cl', '%1$s', 'Juan Pérez', 'EMPLEADO', 1),
            (3, 'm.gonzalez@regattend.cl', '%1$s', 'María González', 'EMPLEADO', 1),
            (4, 'c.soto@regattend.cl', '%1$s', 'Camila Soto', 'EMPLEADO', 1),
            (5, 'p.rojas@regattend.cl', '%1$s', 'Pedro Rojas', 'EMPLEADO', 1),
            (6, 'd.contreras@regattend.cl', '%1$s', 'Daniela Contreras', 'EMPLEADO', 1),
            (7, 'f.tapia@regattend.cl', '%1$s', 'Felipe Tapia', 'EMPLEADO', 1),
            (8, 'v.silva@regattend.cl', '%1$s', 'Valentina Silva', 'EMPLEADO', 1),
            (9, 'm.castillo@regattend.cl', '%1$s', 'Matías Castillo', 'EMPLEADO', 1),
            (10, 'a.sepulveda@regattend.cl', '%1$s', 'Andrea Sepúlveda', 'EMPLEADO', 1),
            (11, 'j.morales@regattend.cl', '%1$s', 'Javier Morales', 'EMPLEADO', 1),
            (12, 'p.fuentes@regattend.cl', '%1$s', 'Paula Fuentes', 'EMPLEADO', 1),
            (13, 'n.herrera@regattend.cl', '%1$s', 'Nicolás Herrera', 'EMPLEADO', 1),
            (14, 's.munoz@regattend.cl', '%1$s', 'Sofía Muñoz', 'EMPLEADO', 1),
            (15, 'd.flores@regattend.cl', '%1$s', 'Diego Flores', 'EMPLEADO', 1),
            (16, 'c.martinez@regattend.cl', '%1$s', 'Carolina Martínez', 'EMPLEADO', 1),
            (17, 'b.vega@regattend.cl', '%1$s', 'Bastián Vega', 'EMPLEADO', 1),
            (18, 'l.gomez@regattend.cl', '%1$s', 'Loreto Gómez', 'EMPLEADO', 1),
            (19, 'h.cruz@regattend.cl', '%1$s', 'Héctor Cruz', 'EMPLEADO', 1),
            (20, 'r.salazar@regattend.cl', '%1$s', 'Romina Salazar', 'EMPLEADO', 1),
            (21, 'g.paredes@regattend.cl', '%1$s', 'Gabriel Paredes', 'EMPLEADO', 1),
            (22, 'a.mendez@regattend.cl', '%1$s', 'Ana Méndez', 'EMPLEADO', 1),
            (23, 'e.navarro@regattend.cl', '%1$s', 'Esteban Navarro', 'EMPLEADO', 1),
            (24, 't.valenzuela@regattend.cl', '%1$s', 'Teresa Valenzuela', 'EMPLEADO', 1),
            (25, 'i.carrasco@regattend.cl', '%1$s', 'Ignacio Carrasco', 'EMPLEADO', 1);
        """.formatted(hashAdmin);

        String asistenciasTest = """
            INSERT OR IGNORE INTO asistencias (id, usuario_id, accion, fecha_hora) VALUES
            (1, 2, 'ENTRADA', '{HOY} 09:15:00'), (2, 2, 'SALIDA', '{HOY} 17:35:00'),
            (3, 3, 'ENTRADA', '{HOY} 08:50:00'), (4, 3, 'SALIDA', '{HOY} 18:00:00'),
            (5, 4, 'ENTRADA', '{HOY} 09:25:00'), (6, 4, 'SALIDA', '{HOY} 17:30:01'),
            (7, 5, 'ENTRADA', '{HOY} 09:00:00'), (8, 5, 'SALIDA', '{HOY} 17:45:00'),
            (9, 6, 'ENTRADA', '{HOY} 09:29:50'), (10, 6, 'SALIDA', '{HOY} 18:15:00'),
            (11, 7, 'ENTRADA', '{HOY} 08:45:00'), (12, 7, 'SALIDA', '{HOY} 17:50:00'),
            (13, 8, 'ENTRADA', '{HOY} 09:10:00'), (14, 8, 'SALIDA', '{HOY} 17:40:00'),
            (15, 9, 'ENTRADA', '{HOY} 09:20:00'), (16, 9, 'SALIDA', '{HOY} 17:31:00'),
            (17, 10, 'ENTRADA', '{HOY} 09:45:00'), (18, 10, 'SALIDA', '{HOY} 17:40:00'),
            (19, 11, 'ENTRADA', '{HOY} 10:15:00'), (20, 11, 'SALIDA', '{HOY} 18:00:00'),
            (21, 12, 'ENTRADA', '{HOY} 09:35:00'), (22, 12, 'SALIDA', '{HOY} 17:35:00'),
            (23, 13, 'ENTRADA', '{HOY} 11:00:00'), (24, 13, 'SALIDA', '{HOY} 18:20:00'),
            (25, 14, 'ENTRADA', '{HOY} 09:31:00'), (26, 14, 'SALIDA', '{HOY} 17:55:00'),
            (27, 15, 'ENTRADA', '{HOY} 09:10:00'), (28, 15, 'SALIDA', '{HOY} 16:30:00'),
            (29, 16, 'ENTRADA', '{HOY} 08:55:00'), (30, 16, 'SALIDA', '{HOY} 17:00:00'),
            (31, 17, 'ENTRADA', '{HOY} 09:20:00'), (32, 17, 'SALIDA', '{HOY} 14:00:00'),
            (33, 18, 'ENTRADA', '{HOY} 09:05:00'), (34, 18, 'SALIDA', '{HOY} 17:15:00'),
            (35, 19, 'ENTRADA', '{HOY} 10:00:00'), (36, 19, 'SALIDA', '{HOY} 15:00:00'),
            (37, 20, 'ENTRADA', '{HOY} 11:30:00'), (38, 20, 'SALIDA', '{HOY} 16:45:00'),
            (39, 21, 'ENTRADA', '{HOY} 09:40:00'), (40, 21, 'SALIDA', '{HOY} 17:29:00');
        """.replace("{HOY}", fechaHoy);

        stmt.execute(usuariosTest);
        stmt.execute(asistenciasTest);
    }
}