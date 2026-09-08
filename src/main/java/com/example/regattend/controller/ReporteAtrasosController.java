package com.example.regattend.controller;

import com.example.regattend.model.entity.Asistencia;
import com.example.regattend.service.AsistenciaService;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ReporteAtrasosController implements Initializable {

    @FXML private Label lblFechaReporte;
    @FXML private TableView<Asistencia> tablaAtrasos;
    @FXML private TableColumn<Asistencia, Integer> colIdUsuario;
    @FXML private TableColumn<Asistencia, LocalDate> colFecha;
    @FXML private TableColumn<Asistencia, LocalTime> colHoraEntrada;

    private final AsistenciaService asistenciaService = new AsistenciaService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDate hoy = LocalDate.now();
        lblFechaReporte.setText("Fecha del Reporte: " + hoy.toString());

        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("usuarioId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colHoraEntrada.setCellValueFactory(new PropertyValueFactory<>("horaEntrada"));

        cargarAtrasos(hoy);
    }

    private void cargarAtrasos(LocalDate fecha) {
        ObservableList<Asistencia> listaAtrasos = FXCollections.observableArrayList(
                asistenciaService.obtenerAtrasosDelDia(fecha)
        );
        tablaAtrasos.setItems(listaAtrasos);
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