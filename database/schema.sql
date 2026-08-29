-- Script de creación de BD y tablas para RegAttend
-- Base de datos: SQLite

CREATE TABLE IF NOT EXISTS usuarios (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    correo TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    nombre TEXT NOT NULL,
    rol TEXT NOT NULL -- 'ADMIN' o 'EMPLEADO'
);

CREATE TABLE IF NOT EXISTS asistencias (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    usuario_id INTEGER NOT NULL,
    tipo TEXT NOT NULL, -- 'ENTRADA' o 'SALIDA'
    fecha_hora DATETIME NOT NULL,
    FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
);

-- Insertar un usuario administrador por defecto para poder iniciar sesión
-- La contraseña es 'admin123' y está hasheada con SHA-256
-- SHA-256 de 'admin123' es '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9'
INSERT OR IGNORE INTO usuarios (id, correo, password, nombre, rol) 
VALUES (1, 'admin@regattend.cl', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'Administrador Principal', 'ADMIN');
