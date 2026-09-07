@echo off
echo =========================================
echo RegAttend - Inicializando aplicacion...
echo =========================================

IF NOT DEFINED JAVA_HOME (
    IF EXIST "C:\Program Files\Java\jdk-24" (
        set "JAVA_HOME=C:\Program Files\Java\jdk-24"
    ) ELSE IF EXIST "C:\Program Files\Java\jdk-25" (
        set "JAVA_HOME=C:\Program Files\Java\jdk-25"
    )
)
set "PATH=%JAVA_HOME%\bin;%PATH%"

REM Compilando y Ejecutando con JavaFX Maven Plugin
call .\mvnw.cmd javafx:run

echo =========================================
echo Ejecucion terminada.
echo =========================================
pause
