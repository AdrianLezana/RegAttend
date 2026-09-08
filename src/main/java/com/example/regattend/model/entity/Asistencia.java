package com.example.regattend.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Asistencia {
    private int id;
    private int usuarioId;
    private LocalDate fecha;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;

    public Asistencia() {}

    public Asistencia(int id, int usuarioId, LocalDate fecha, LocalTime horaEntrada, LocalTime horaSalida) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
}
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraEntrada() { return horaEntrada; }
    public void setHoraEntrada(LocalTime horaEntrada) { this.horaEntrada = horaEntrada; }

    public LocalTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalTime horaSalida) { this.horaSalida = horaSalida; }
}
