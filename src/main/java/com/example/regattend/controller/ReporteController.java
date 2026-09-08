package com.example.regattend.controller;

import com.example.regattend.model.entity.Usuario;
import com.example.regattend.service.AsistenciaService;
import com.example.regattend.service.ReporteExportService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReporteController implements Initializable {

    @FXML private Label lblFechaReporte;
    @FXML private TableView<Usuario> tablaInasistencias;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colCorreo;

    private final AsistenciaService asistenciaService = new AsistenciaService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDate hoy = LocalDate.now();
        lblFechaReporte.setText("Fecha del Reporte: " + hoy.toString());

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        cargarInasistencias(hoy);
    }

    private void cargarInasistencias(LocalDate fecha) {
        ObservableList<Usuario> listaInasistentes = FXCollections.observableArrayList(
                asistenciaService.obtenerInasistenciasDelDia(fecha)
        );
        tablaInasistencias.setItems(listaInasistentes);
    }

    @FXML
    private void handleExportarCsv(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV (*.csv)", "*.csv"));
        fileChooser.setInitialFileName("reporte_inasistencias_" + LocalDate.now() + ".csv");

        File archivo = fileChooser.showSaveDialog(tablaInasistencias.getScene().getWindow());
        if (archivo != null) {
            boolean exito = ReporteExportService.exportarInasistenciasCsv(archivo.getAbsolutePath(), tablaInasistencias.getItems());
            if (exito) {
                lblFechaReporte.setText("Reporte exportado exitosamente a: " + archivo.getName());
            } else {
                lblFechaReporte.setText("Error al exportar el archivo CSV.");
            }
        }
    }

    @FXML
    private void handleVolver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/regattend/view/usuarios-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("RegAttend - Panel Principal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}