package com.example.regattend.controller;

import com.example.regattend.App;
import com.example.regattend.model.dao.AsistenciaDAO;
import com.example.regattend.model.entity.ReporteItem;
import com.example.regattend.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;

public class ReporteController {
    @FXML private TableView<ReporteItem> tablaReportes;

    private AsistenciaDAO asistenciaDAO = new AsistenciaDAO();

    @FXML void gestionarUsuarios() throws Exception {
        App.setRoot("view/usuarios-view");
    }

    @FXML void generarReporteAtrasos() {
        tablaReportes.setItems(FXCollections.observableArrayList(asistenciaDAO.obtenerReporteAtrasos()));
    }

    @FXML void generarReporteSalidasAnticipadas() {
        tablaReportes.setItems(FXCollections.observableArrayList(asistenciaDAO.obtenerReporteSalidasAnticipadas()));
    }

    @FXML void generarReporteInasistencias() {
        tablaReportes.setItems(FXCollections.observableArrayList(asistenciaDAO.obtenerReporteInasistencias(LocalDate.now())));
    }

    @FXML void cerrarSesion() throws Exception {
        SessionManager.getInstance().cerrarSesion();
        App.setRoot("view/login-view");
    }

    @FXML
    void exportarExcel() {
        // Verificar si la tabla tiene datos
        if (tablaReportes.getItems().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "No hay datos en la tabla para exportar.").show();
            return;
        }

        // Abrir ventana para que el usuario elija dónde guardar
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.setTitle("Guardar Reporte Excel");
        fileChooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("Archivos Excel (*.xlsx)", "*.xlsx"));
        fileChooser.setInitialFileName("Reporte_Asistencia.xlsx");

        // Capturar la ventana actual (Stage)
        javafx.stage.Stage stage = (javafx.stage.Stage) tablaReportes.getScene().getWindow();
        java.io.File archivoDestino = fileChooser.showSaveDialog(stage);

        if (archivoDestino != null) {
            // Extraer la lista de la tabla y mandarla al servicio
            java.util.List<ReporteItem> datos = new java.util.ArrayList<>(tablaReportes.getItems());
            boolean exito = com.example.regattend.service.ReporteExportService.exportarAExcel(datos, archivoDestino);

            if (exito) {
                new Alert(Alert.AlertType.INFORMATION, "Reporte exportado exitosamente en:\n" + archivoDestino.getAbsolutePath()).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Ocurrió un error al guardar el archivo Excel.").show();
            }
        }
    }
}