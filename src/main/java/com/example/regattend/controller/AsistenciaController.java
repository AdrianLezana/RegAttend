package com.example.regattend.controller;
import com.example.regattend.App;
import com.example.regattend.model.dao.AsistenciaDAO;
import com.example.regattend.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AsistenciaController {
    private AsistenciaDAO asistenciaDAO = new AsistenciaDAO();

    @FXML void marcarEntrada() { registrar("ENTRADA"); }
    @FXML void marcarSalida() { registrar("SALIDA"); }

    private void registrar(String accion) {
        int id = SessionManager.getInstance().getUsuarioActual().getId();

        // Validar que no repita la misma acción el mismo día
        if (asistenciaDAO.yaMarcoHoy(id, accion)) {
            new Alert(Alert.AlertType.WARNING, "Ya registraste tu " + accion + " el día de hoy.").show();
            return;
        }

        // Validar orden lógico: No puede salir si no ha entrado
        if (accion.equals("SALIDA") && !asistenciaDAO.yaMarcoHoy(id, "ENTRADA")) {
            new Alert(Alert.AlertType.WARNING, "Debes registrar tu ENTRADA antes de poder registrar la SALIDA.").show();
            return;
        }

        // Ejecutar el registro
        if (asistenciaDAO.registrarAsistencia(id, accion)) {
            new Alert(Alert.AlertType.INFORMATION, accion + " registrada con éxito.").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error al guardar el registro en el sistema.").show();
        }
    }

    @FXML void cerrarSesion() throws Exception {
        SessionManager.getInstance().cerrarSesion();
        App.setRoot("view/login-view");
    }
}