# RegAttend - Sistema de Registro de Asistencia

MVP de escritorio para el control de asistencia de empleados y generación de reportes administrativos para una empresa de productos químicos.

---

## Stack Tecnológico

* **Lenguaje:** Java (JDK 21 o superior)
* **Interfaz Gráfica:** JavaFX (ControlsFX, BootstrapFX)
* **Arquitectura:** Modelo-Vista-Controlador (MVC)
* **Gestor de Dependencias:** Apache Maven
* **Base de Datos:** MySQL / MariaDB (XAMPP)
* **Control de Versiones:** Git & GitHub

---

## Requerimientos del Sistema

* **CA-01:** Control de asistencia (Registro de entrada y salida mediante botón).
* **GU-01 al GU-03:** CRUD completo de usuarios (Solo Administrador).
* **RE-01:** Reporte de atrasos (Entradas posteriores a las 09:30 AM).
* **RE-02:** Reporte de salidas anticipadas (Salidas previas a las 17:30 PM).
* **RE-03:** Reporte de inasistencias diarias.

---

## Configuración y Ejecución Local

### 1. Requisitos Previos
* Tener instalado **XAMPP** con el servicio de MySQL activo en el puerto `3306`.
* Tener configurado el JDK en IntelliJ IDEA.

### 2. Base de Datos
1. Iniciar **Apache** y **MySQL** desde el panel de control de XAMPP.
2. Acceder a `http://localhost/phpmyadmin/`.
3. Crear una base de datos llamada `regattend_db`.
4. Importar el script SQL disponible en `/database/schema.sql`.

### 3. Ejecución
* Abrir el proyecto en IntelliJ IDEA.
* Esperar a que Maven descargue las dependencias (`pom.xml`).
* Ejecutar la clase `Launcher.java`.

---

## Flujo de Trabajo en Git (Equipo)

Para mantener el orden entre los integrantes:
* **Rama `main`:** Código de producción estable y probado.
* **Ramas por funcionalidad:** `feature/nombre-de-la-tarea` (ej: `feature/login-view`, `feature/usuario-dao`).
* **Regla:** Antes de comenzar una tarea, actualizar la rama base ejecutando:
  ```bash
  git checkout main
  git pull origin main
