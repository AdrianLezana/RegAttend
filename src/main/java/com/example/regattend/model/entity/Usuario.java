package com.example.regattend.model.entity;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String rol;
    private boolean activo;

    public Usuario(int id, String nombre, String correo, String rol, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.activo = activo;
    }

    // --- GETTERS ---
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getRol() {
        return rol;
    }

    public boolean isActivo() {
        return activo;
    }

    // --- SETTERS ---
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}