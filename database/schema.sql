-- 1. Eliminar tablas previas en caso de reinicio limpio (orden inverso por FK)
DROP TABLE IF EXISTS asistencias;
DROP TABLE IF EXISTS usuarios;

-- 2. Crear tabla de Usuarios
CREATE TABLE usuarios (
                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                          nombre TEXT NOT NULL,
                          correo TEXT NOT NULL UNIQUE,
                          password_hash TEXT NOT NULL,
                          rol TEXT NOT NULL DEFAULT 'EMPLEADO',
                          activo INTEGER NOT NULL DEFAULT 1,
                          fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Crear tabla de Asistencias
CREATE TABLE asistencias (
                             id INTEGER PRIMARY KEY AUTOINCREMENT,
                             usuario_id INTEGER NOT NULL,
                             fecha TEXT NOT NULL,
                             hora_entrada TEXT NULL,
                             hora_salida TEXT NULL,
                             CONSTRAINT fk_asistencia_usuario
                                 FOREIGN KEY (usuario_id)
                                     REFERENCES usuarios(id)
                                     ON UPDATE CASCADE
                                     ON DELETE RESTRICT,
                             CONSTRAINT uq_usuario_fecha
                                 UNIQUE (usuario_id, fecha)
);

-- 4. Crear índice para optimizar consultas y reportes por rangos de fecha
CREATE INDEX idx_asistencia_fecha ON asistencias(fecha);

-- 5. Insertar usuario administrador por defecto (Contraseña: admin123)
INSERT INTO usuarios (nombre, correo, password_hash, rol, activo)
VALUES ('Administrador General', 'admin@regattend.com', 'admin123', 'ADMIN', 1);