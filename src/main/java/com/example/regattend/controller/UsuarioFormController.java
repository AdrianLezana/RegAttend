package com.example.regattend.controller;

import com.example.regattend.model.dao.UsuarioDAO;
import com.example.regattend.model.entity.Usuario;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioFormController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cmbRol;
    @FXML private Label lblMensaje;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbRol.setItems(FXCollections.observableArrayList("ADMIN", "EMPLEADO"));
        cmbRol.setValue("EMPLEADO");
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String password = txtPassword.getText();
        String rol = cmbRol.getValue();

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Por favor, complete todos los campos.");
            return;
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setCorreo(correo);
        nuevo.setPasswordHash(password); // En etapas iniciales se almacena directo, escalable a hash
        nuevo.setRol(rol);
        nuevo.setActivo(true);

        boolean exito = usuarioDAO.crear(nuevo);
        if (exito) {
            regresarAlPanel(event);
        } else {
            lblMensaje.setText("Error al registrar usuario (correo duplicado o fallo de BD).");
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        regresarAlPanel(event);
    }

    private void regresarAlPanel(ActionEvent event) {
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