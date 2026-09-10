package com.example.regattend.controller;
import com.example.regattend.App;
import com.example.regattend.model.dao.AsistenciaDAO;
import com.example.regattend.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.time.LocalDate;

public class ReporteController {
    @FXML private ListView<String> listViewReportes;
    private AsistenciaDAO asistenciaDAO = new AsistenciaDAO();

    @FXML void gestionarUsuarios() throws Exception {
        App.setRoot("view/usuarios-view");
    }

    @FXML void generarReporteAtrasos() {
        listViewReportes.getItems().setAll(asistenciaDAO.obtenerReporteAtrasos());
    }
    @FXML void generarReporteSalidasAnticipadas() {
        listViewReportes.getItems().setAll(asistenciaDAO.obtenerReporteSalidasAnticipadas());
    }
    @FXML void generarReporteInasistencias() {
        listViewReportes.getItems().setAll(asistenciaDAO.obtenerReporteInasistencias(LocalDate.now()));
    }
    @FXML void cerrarSesion() throws Exception {
        SessionManager.getInstance().cerrarSesion();
        App.setRoot("view/login-view");
    }
}