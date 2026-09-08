package com.example.regattend.model.dao;

import com.example.regattend.model.entity.Asistencia;
import com.example.regattend.model.entity.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IAsistenciaDAO {
    boolean registrarEntrada(int usuarioId, LocalDate fecha, LocalTime horaEntrada);
    boolean registrarSalida(int usuarioId, LocalDate fecha, LocalTime horaSalida);
    Asistencia obtenerPorUsuarioYFecha(int usuarioId, LocalDate fecha);
    List<Usuario> listarInasistencias(LocalDate fecha);
    List<Asistencia> listarAtrasos(LocalDate fechaLimite, LocalTime limiteAtraso);
    List<Asistencia> listarSalidasAnticipadas(LocalDate fechaLimite, LocalTime limiteSalida);
}