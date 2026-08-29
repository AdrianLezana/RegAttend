package com.example.regattend.model.entity;

/**
 * Clase que representa un Usuario en el sistema.
 */
public class Usuario {
    private int id;
    private String correo;
    private String password;
    private String nombre;
    private String rol; // 'ADMIN' o 'EMPLEADO'

    public Usuario() {}

    public Usuario(int id, String correo, String password, String nombre, String rol) {
        this.id = id;
        this.correo = correo;
        this.password = password;
        this.nombre = nombre;
        this.rol = rol;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    @Override
    public String toString() {
        return nombre + " (" + correo + ") - " + rol;
    }
}
