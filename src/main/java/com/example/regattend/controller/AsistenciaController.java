package com.example.regattend.controller;

import com.example.regattend.model.entity.Asistencia;
import com.example.regattend.model.entity.Usuario;
import com.example.regattend.service.AsistenciaService;
import com.example.regattend.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AsistenciaController implements Initializable {

    @FXML private Label lblUsuarioLogueado;
    @FXML private Label lblEstadoHoy;
    @FXML private Label lblMensajeAccion;
    @FXML private Button btnMarcarEntrada;
    @FXML private Button btnMarcarSalida;

    private final AsistenciaService asistenciaService = new AsistenciaService();
    private Usuario usuarioActual;
    private LocalDate hoy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usuarioActual = SessionManager.getUsuarioActual();
        hoy = LocalDate.now();

        if (usuarioActual != null) {
            lblUsuarioLogueado.setText("Usuario: " + usuarioActual.getNombre() + " (" + usuarioActual.getRol() + ")");
        }

        actualizarEstadoVisual();
    }

    private void actualizarEstadoVisual() {
        Asistencia registro = asistenciaService.obtenerRegistroHoy(usuarioActual.getId(), hoy);
        if (registro == null) {
            lblEstadoHoy.setText("Estado de hoy (" + hoy + "): Sin entrada registrada.");
            btnMarcarEntrada.setDisable(false);
            btnMarcarSalida.setDisable(true);
        } else {
            String info = "Entrada: " + registro.getHoraEntrada();
            if (registro.getHoraSalida() != null) {
                info += " | Salida: " + registro.getHoraSalida();
                btnMarcarEntrada.setDisable(true);
                btnMarcarSalida.setDisable(true);
                lblMensajeAccion.setText("Jornada de hoy completada.");
            } else {
                btnMarcarEntrada.setDisable(true);
                btnMarcarSalida.setDisable(false);
            }
            lblEstadoHoy.setText(info);
        }
    }

    @FXML
    private void handleMarcarEntrada(ActionEvent event) {
        boolean exito = asistenciaService.marcarEntradaEmpleado(usuarioActual.getId(), hoy);
        if (exito) {
            lblMensajeAccion.setStyle("-fx-text-fill: #27ae60;");
            lblMensajeAccion.setText("¡Entrada registrada exitosamente!");
            actualizarEstadoVisual();
        } else {
            lblMensajeAccion.setStyle("-fx-text-fill: #c0392b;");
            lblMensajeAccion.setText("Error: Ya existe un registro de entrada para hoy.");
        }
    }

    @FXML
    private void handleMarcarSalida(ActionEvent event) {
        boolean exito = asistenciaService.marcarSalidaEmpleado(usuarioActual.getId(), hoy);
        if (exito) {
            lblMensajeAccion.setStyle("-fx-text-fill: #27ae60;");
            lblMensajeAccion.setText("¡Salida registrada exitosamente!");
            actualizarEstadoVisual();
        } else {
            lblMensajeAccion.setStyle("-fx-text-fill: #c0392b;");
            lblMensajeAccion.setText("Error al registrar la salida.");
        }
    }

    @FXML
    private void handleVolverPanel(ActionEvent event) {
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