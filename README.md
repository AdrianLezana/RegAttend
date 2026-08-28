# RegAttend - Sistema de Registro de Asistencia

MVP de escritorio para el control de asistencia de empleados y generación de reportes administrativos para una empresa de productos químicos.

---

## Stack Tecnológico

* **Lenguaje:** Java (JDK 21 o superior)
* **Gestor de Dependencias:** Apache Maven
* **Base de Datos:** SQLite
* **Control de Versiones:** Git & GitHub

---

## Requerimientos del Sistema (Avance 2)

* **GU-01 al GU-03:** Base de datos preparada para CRUD de usuarios.
* **Modelo Relacional:** Script de creación de base de datos (`database/schema.sql` y `database/schema.txt`).
* **Conexión:** Verificación de conexión en Java mediante `DatabaseConnection.java`.

---

## Configuración y Ejecución Local

### 1. Requisitos Previos
* Tener instalado el JDK 21 o superior.
* No se requiere instalar motores de bases de datos pesados (XAMPP/MySQL) ya que estamos utilizando SQLite para mayor portabilidad.

### 2. Base de Datos
* La base de datos `regattend.db` se autogenerará en la raíz del proyecto al ejecutar el código. 
* El archivo con las sentencias SQL que crea las tablas se encuentra en `database/schema.sql`.

### 3. Ejecución en VS Code
1. Clona este repositorio o descomprime el proyecto.
2. Abre la carpeta del proyecto en Visual Studio Code.
3. Puedes ejecutar la clase principal haciendo clic en `Run` sobre el archivo `Launcher.java`.
4. Alternativamente, puedes hacer doble clic en el archivo `run.bat` que compilará y ejecutará el proyecto por ti.

---

## Flujo de Trabajo en Git (Equipo)

Para mantener el orden entre los integrantes:
* **Rama `main`:** Código de producción estable y probado.
* **Ramas por funcionalidad:** `feature/nombre-de-la-tarea`.
* **Regla:** Antes de comenzar una tarea, recuerda hacer `git pull origin main`.
