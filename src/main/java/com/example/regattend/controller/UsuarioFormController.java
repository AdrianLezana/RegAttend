package com.example.regattend.controller;

import com.example.regattend.App;
import com.example.regattend.model.dao.UsuarioDAO;
import com.example.regattend.model.entity.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UsuarioFormController {
    @FXML private Label lblTitulo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> cbRol;

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    public static Usuario usuarioAEditar = null;

    @FXML
    public void initialize() {
        cbRol.getItems().addAll("EMPLEADO", "ADMIN");

        if (usuarioAEditar != null) {
            lblTitulo.setText("Editar Usuario");
            txtNombre.setText(usuarioAEditar.getNombre());
            txtCorreo.setText(usuarioAEditar.getCorreo());
            cbRol.setValue(usuarioAEditar.getRol());
            txtPassword.setDisable(true); // No cambiamos la pass en edición básica
        } else {
            lblTitulo.setText("Nuevo Usuario");
        }
    }

    @FXML void guardarUsuario() throws Exception {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String rol = cbRol.getValue();

        if (nombre.isEmpty() || correo.isEmpty() || rol == null) {
            new Alert(Alert.AlertType.ERROR, "Llene los campos obligatorios").show();
            return;
        }

        boolean exito;
        if (usuarioAEditar == null) {
            String pass = txtPassword.getText();
            exito = usuarioDAO.crearUsuario(nombre, correo, pass, rol);
        } else {
            exito = usuarioDAO.actualizarUsuario(usuarioAEditar.getId(), nombre, correo, rol);
        }

        if (exito) {
            new Alert(Alert.AlertType.INFORMATION, "Usuario guardado correctamente").show();
            App.setRoot("view/usuarios-view");
        } else {
            new Alert(Alert.AlertType.ERROR, "Error al guardar en la base de datos").show();
        }
    }

    @FXML void cancelar() throws Exception {
        App.setRoot("view/usuarios-view");
    }
}