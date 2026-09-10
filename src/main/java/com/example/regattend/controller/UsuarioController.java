package com.example.regattend.controller;

import com.example.regattend.App;
import com.example.regattend.model.dao.UsuarioDAO;
import com.example.regattend.model.entity.Usuario;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

public class UsuarioController {
    @FXML private TableView<Usuario> tablaUsuarios;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        tablaUsuarios.setItems(FXCollections.observableArrayList(usuarioDAO.listarUsuariosActivos()));
    }

    @FXML void abrirFormularioNuevo() throws Exception {
        UsuarioFormController.usuarioAEditar = null; // Indicamos que es modo Creación
        App.setRoot("view/nuevo-usuario-view");
    }

    @FXML void abrirFormularioEditar() throws Exception {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            UsuarioFormController.usuarioAEditar = seleccionado; // Pasamos el usuario a editar
            App.setRoot("view/nuevo-usuario-view");
        } else {
            new Alert(Alert.AlertType.WARNING, "Seleccione un usuario de la tabla").show();
        }
    }

    @FXML void eliminarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            if (usuarioDAO.eliminarUsuario(seleccionado.getId())) {
                new Alert(Alert.AlertType.INFORMATION, "Usuario eliminado correctamente.").show();
                cargarUsuarios();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "Seleccione un usuario para eliminar.").show();
        }
    }

    @FXML void volver() throws Exception {
        App.setRoot("view/reportes-view");
    }
}