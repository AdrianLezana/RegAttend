package com.example.regattend.controller;

import com.example.regattend.model.dao.UsuarioDAO;
import com.example.regattend.model.entity.Usuario;
import com.example.regattend.util.PasswordHasher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de gestión (CRUD) de Usuarios.
 */
public class UsuarioController implements Initializable {

    // Componentes FXML de la Tabla
    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario, Integer> colId;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, String> colCorreo;
    @FXML
    private TableColumn<Usuario, String> colRol;

    // Componentes FXML del Formulario
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private ComboBox<String> cmbRol;

    // Botones de acción
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnLimpiar;

    // Acceso a datos y lista observable
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    private Usuario usuarioSeleccionado = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. Configurar las columnas de la tabla con los atributos de la clase Usuario
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        // Centrar columna de ID y Rol
        colId.setStyle("-fx-alignment: CENTER;");
        colRol.setStyle("-fx-alignment: CENTER;");

        // 2. Configurar opciones del ComboBox de Roles
        cmbRol.setItems(FXCollections.observableArrayList("ADMIN", "EMPLEADO"));

        // 3. Listener para detectar cuando se selecciona un usuario en la tabla
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                usuarioSeleccionado = newSelection;
                cargarDatosEnFormulario(newSelection);
            }
        });

        // 4. Cargar los usuarios desde la base de datos a la tabla
        cargarUsuarios();
    }

    /**
     * Carga todos los usuarios desde la base de datos a la tabla.
     */
    private void cargarUsuarios() {
        listaUsuarios.clear();
        List<Usuario> usuariosBD = usuarioDAO.getAllUsuarios();
        listaUsuarios.addAll(usuariosBD);
        tablaUsuarios.setItems(listaUsuarios);
    }

    /**
     * Carga los datos del usuario seleccionado en los campos del formulario.
     */
    private void cargarDatosEnFormulario(Usuario usuario) {
        txtNombre.setText(usuario.getNombre());
        txtCorreo.setText(usuario.getCorreo());
        txtPassword.clear(); // Por seguridad no mostramos la contraseña hasheada
        cmbRol.setValue(usuario.getRol());
    }

    /**
     * Acción para CREAR un nuevo usuario.
     */
    @FXML
    private void onGuardar(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String password = txtPassword.getText().trim();
        String rol = cmbRol.getValue();

        // Validaciones básicas
        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || rol == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos",
                    "Por favor completa todos los campos para registrar el usuario.");
            return;
        }

        // Hasheamos la contraseña con SHA-256
        String passwordHasheada = PasswordHasher.hashPassword(password);

        Usuario nuevoUsuario = new Usuario(0, correo, passwordHasheada, nombre, rol);

        boolean creado = usuarioDAO.createUsuario(nuevoUsuario);
        if (creado) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario creado correctamente.");
            cargarUsuarios();
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No se pudo crear el usuario. Es posible que el correo ya esté registrado.");
        }
    }

    /**
     * Acción para EDITAR / ACTUALIZAR un usuario seleccionado.
     */
    @FXML
    private void onActualizar(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin Selección",
                    "Selecciona un usuario de la tabla para actualizar.");
            return;
        }

        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String password = txtPassword.getText().trim();
        String rol = cmbRol.getValue();

        if (nombre.isEmpty() || correo.isEmpty() || rol == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "El nombre, correo y rol son obligatorios.");
            return;
        }

        // Si el campo de contraseña no está vacío, la actualizamos hasheada; si está
        // vacío, conservamos la actual
        String passwordFinal = password.isEmpty()
                ? usuarioSeleccionado.getPassword()
                : PasswordHasher.hashPassword(password);

        Usuario usuarioModificado = new Usuario(
                usuarioSeleccionado.getId(),
                correo,
                passwordFinal,
                nombre,
                rol);

        boolean actualizado = usuarioDAO.updateUsuario(usuarioModificado);
        if (actualizado) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario actualizado correctamente.");
            cargarUsuarios();
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el usuario.");
        }
    }

    /**
     * Acción para ELIMINAR un usuario seleccionado.
     */
    @FXML
    private void onEliminar(ActionEvent event) {
        if (usuarioSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin Selección", "Selecciona un usuario de la tabla para eliminar.");
            return;
        }

        // Confirmación previa
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Estás seguro de eliminar a " + usuarioSeleccionado.getNombre() + "?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            boolean eliminado = usuarioDAO.deleteUsuario(usuarioSeleccionado.getId());
            if (eliminado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario eliminado correctamente.");
                cargarUsuarios();
                limpiarCampos();
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el usuario.");
            }
        }
    }

    /**
     * Acción para LIMPIAR el formulario y deseleccionar la tabla.
     */
    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    /**
     * Limpia los campos del formulario y resetea la selección actual.
     */
    private void limpiarCampos() {
        txtNombre.clear();
        txtCorreo.clear();
        txtPassword.clear();
        cmbRol.getSelectionModel().clearSelection();
        tablaUsuarios.getSelectionModel().clearSelection();
        usuarioSeleccionado = null;
    }

    /**
     * Método utilitario para mostrar cuadros de diálogo / alertas.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
