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

    @FXML private Label lblEstado;
    @FXML private Button btnEntrada;
    @FXML private Button btnSalida;

    private final AsistenciaService asistenciaService = new AsistenciaService();
    private Usuario usuarioActual;
    private LocalDate hoy;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usuarioActual = SessionManager.getUsuarioActual();
        hoy = LocalDate.now();
        verificarEstadoActual();
    }

    private void verificarEstadoActual() {
        Asistencia asistencia = asistenciaService.obtenerRegistroHoy(usuarioActual.getId(), hoy);
        if (asistencia != null) {
            String estado = "Entrada: " + (asistencia.getHoraEntrada() != null ? asistencia.getHoraEntrada() : "Pendiente");
            if (asistencia.getHoraSalida() != null) {
                estado += " | Salida: " + asistencia.getHoraSalida();
                btnEntrada.setDisable(true);
                btnSalida.setDisable(true);
            } else {
                btnEntrada.setDisable(true);
            }
            lblEstado.setText(estado);
        } else {
            btnSalida.setDisable(true);
            lblEstado.setText("Sin registro para el día de hoy.");
        }
    }

    @FXML
    private void handleRegistrarEntrada(ActionEvent event) {
        boolean exito = asistenciaService.marcarEntradaEmpleado(usuarioActual.getId(), hoy);
        if (exito) {
            lblEstado.setText("¡Entrada registrada con éxito!");
            btnEntrada.setDisable(true);
            btnSalida.setDisable(false);
        } else {
            lblEstado.setText("Error al registrar la entrada.");
        }
    }

    @FXML
    private void handleRegistrarSalida(ActionEvent event) {
        boolean exito = asistenciaService.marcarSalidaEmpleado(usuarioActual.getId(), hoy);
        if (exito) {
            lblEstado.setText("¡Salida registrada con éxito!");
            btnSalida.setDisable(true);
        } else {
            lblEstado.setText("Error al registrar la salida.");
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