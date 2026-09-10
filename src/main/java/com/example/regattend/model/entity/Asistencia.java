package com.example.regattend.model.entity;
import java.time.LocalDateTime;

public class Asistencia {
    private int id;
    private int usuarioId;
    private String accion; // "ENTRADA" o "SALIDA"
    private LocalDateTime fechaHora;
}