package com.example.regattend.util;
import com.example.regattend.model.entity.Usuario;

public class SessionManager {
    private static SessionManager instance;
    private Usuario usuarioActual;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) { instance = new SessionManager(); }
        return instance;
    }

    public void setUsuarioActual(Usuario usuario) { this.usuarioActual = usuario; }
    public Usuario getUsuarioActual() { return usuarioActual; }
    public void cerrarSesion() { usuarioActual = null; }
    public boolean isAdministrador() { return usuarioActual != null && "ADMIN".equals(usuarioActual.getRol()); }
}