@echo off
echo =========================================
echo RegAttend - Inicializando aplicacion...
echo =========================================

REM Usando la version de Java 21 instalada
set JAVA_HOME=C:\Users\danie\.jdks\temurin-21.0.10
set PATH=%JAVA_HOME%\bin;%PATH%

REM Compilando y Ejecutando a traves del Wrapper de Maven
call .\mvnw.cmd clean compile exec:java -Dexec.mainClass="com.example.regattend.Launcher"

echo =========================================
echo Ejecucion terminada.
echo =========================================
pause
