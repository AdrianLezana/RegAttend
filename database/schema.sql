CREATE TABLE IF NOT EXISTS usuarios (
                                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                                        nombre TEXT NOT NULL,
                                        correo TEXT UNIQUE NOT NULL,
                                        password TEXT NOT NULL,
                                        rol TEXT NOT NULL,
                                        activo INTEGER DEFAULT 1
);

CREATE TABLE IF NOT EXISTS asistencias (
                                           id INTEGER PRIMARY KEY AUTOINCREMENT,
                                           usuario_id INTEGER NOT NULL,
                                           accion TEXT NOT NULL,
                                           fecha_hora TEXT NOT NULL,
                                           FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

INSERT INTO usuarios (nombre, correo, password, rol, activo)
VALUES ('Admin', 'Alezana@regattend.cl', 'alezana', 'ADMIN', 1);

INSERT INTO usuarios (nombre, correo, password, rol, activo)
VALUES ('Empleado 1', 'Cvillaverde@regattend.cl', 'cvillaverde', 'EMPLEADO', 1);