package com.example.regattend.controller;
import com.example.regattend.App;
import com.example.regattend.model.dao.UsuarioDAO;
import com.example.regattend.model.entity.Usuario;
import com.example.regattend.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML void ingresar() throws Exception {
        Usuario usuario = usuarioDAO.validarLogin(txtCorreo.getText(), txtPassword.getText());
        if (usuario != null) {
            SessionManager.getInstance().setUsuarioActual(usuario);
            if (usuario.getRol().equals("ADMIN")) {
                App.setRoot("view/reportes-view"); // Redirige a módulo Admin (RE-01, RE-02, RE-03)
            } else {
                App.setRoot("view/asistencia-view"); // Redirige a módulo Empleado (CA-01)
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Credenciales incorrectas");
            alert.show();
        }
    }
}