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
        if (asistenciaDAO.registrarAsistencia(id, accion)) {
            new Alert(Alert.AlertType.INFORMATION, accion + " registrada con éxito.").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error al registrar.").show();
        }
    }

    @FXML void cerrarSesion() throws Exception {
        SessionManager.getInstance().cerrarSesion();
        App.setRoot("view/login-view");
    }
}