package com.example.regattend.controller;

import com.example.regattend.model.dao.UsuarioDAO;
import com.example.regattend.model.entity.Usuario;
import com.example.regattend.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable {

    @FXML private Label lblBienvenida;
    @FXML private Button btnIrReportes;
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colCorreo;
    @FXML private TableColumn<Usuario, String> colRol;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Usuario actual = SessionManager.getUsuarioActual();
        if (actual != null) {
            lblBienvenida.setText("Usuario: " + actual.getNombre() + " (" + actual.getRol() + ")");

            // Restringir el botón de reportes solo a administradores
            if (!"ADMIN".equalsIgnoreCase(actual.getRol())) {
                btnIrReportes.setVisible(false);
                btnIrReportes.setManaged(false);
            }
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        cargarUsuarios();
    }

    private void cargarUsuarios() {
        ObservableList<Usuario> lista = FXCollections.observableArrayList(usuarioDAO.listarActivos());
        tablaUsuarios.setItems(lista);
    }

    @FXML
    private void handleIrAsistencia(ActionEvent event) {
        cambiarEscena(event, "/com/example/regattend/view/asistencia-view.fxml", "RegAttend - Registro de Asistencia");
    }

    @FXML
    private void handleIrReportes(ActionEvent event) {
        cambiarEscena(event, "/com/example/regattend/view/reportes-view.fxml", "RegAttend - Reporte de Inasistencias");
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        SessionManager.cerrarSesion();
        cambiarEscena(event, "/com/example/regattend/view/login-view.fxml", "RegAttend - Login");
    }

    private void cambiarEscena(ActionEvent event, String fxmlPath, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}