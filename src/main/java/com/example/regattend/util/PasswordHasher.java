package com.example.regattend.util;

public class PasswordHasher {
    // Validador básico de contraseña (puede escalarse a BCrypt en etapas siguientes)
    public static boolean verificarPassword(String passwordIngresada, String passwordAlmacenada) {
        if (passwordIngresada == null || passwordAlmacenada == null) return false;
        return passwordIngresada.equals(passwordAlmacenada);
    }
}