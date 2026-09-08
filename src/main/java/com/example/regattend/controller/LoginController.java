package com.example.regattend.controller;

import com.example.regattend.model.dao.UsuarioDAO;
import com.example.regattend.model.entity.Usuario;
import com.example.regattend.util.PasswordHasher;
import com.example.regattend.util.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMensaje;
    @FXML private Button btnLogin;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void handleLogin(ActionEvent event) {
        String correo = txtCorreo.getText().trim();
        String password = txtPassword.getText();

        if (correo.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Por favor, complete todos los campos.");
            return;
        }

        Usuario usuario = usuarioDAO.buscarPorCorreo(correo);
        if (usuario != null && PasswordHasher.verificarPassword(password, usuario.getPasswordHash())) {
            if (usuario.getActivo() == 0) {
                lblMensaje.setText("El usuario se encuentra inactivo.");
                return;
            }
            SessionManager.setUsuarioActual(usuario);

            // Redirección basada estrictamente en el rol del usuario
            String vistaDestino = "ADMIN".equalsIgnoreCase(usuario.getRol())
                    ? "/com/example/regattend/view/usuarios-view.fxml"
                    : "/com/example/regattend/view/asistencia-view.fxml";

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(vistaDestino));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("RegAttend - Panel " + usuario.getRol());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                lblMensaje.setText("Error al cargar la vista principal.");
            }
        } else {
            lblMensaje.setText("Credenciales inválidas o usuario no encontrado.");
        }
    }
}