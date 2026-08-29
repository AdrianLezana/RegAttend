package com.example.regattend.util;

import com.example.regattend.model.entity.Usuario;

/**
 * Almacena el usuario autenticado actualmente en el sistema.
 */
public class SessionManager {
    private static Usuario currentUser;

    public static void setCurrentUser(Usuario user) {
        currentUser = user;
    }

    public static Usuario getCurrentUser() {
        return currentUser;
    }

    public static void clearSession() {
        currentUser = null;
    }
}
