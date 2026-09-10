package com.example.regattend.model.entity;

public class ReporteItem {
    private String nombre;
    private String fecha;
    private String hora;
    private String detalle;

    public ReporteItem(String nombre, String fecha, String hora, String detalle) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
        this.detalle = detalle;
    }

    public String getNombre() { return nombre; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getDetalle() { return detalle; }
}