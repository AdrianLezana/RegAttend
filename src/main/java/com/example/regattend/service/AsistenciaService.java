package com.example.regattend.service;

import com.example.regattend.model.dao.AsistenciaDAO;
import com.example.regattend.model.entity.Asistencia;
import com.example.regattend.model.entity.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AsistenciaService {
    public static final LocalTime LIMITE_ATRASO = LocalTime.of(9, 30, 0);
    public static final LocalTime LIMITE_SALIDA = LocalTime.of(17, 30, 0);

    private final AsistenciaDAO asistenciaDAO = new AsistenciaDAO();

    public boolean marcarEntradaEmpleado(int usuarioId, LocalDate fecha) {
        return asistenciaDAO.registrarEntrada(usuarioId, fecha, LocalTime.now());
    }

    public boolean marcarSalidaEmpleado(int usuarioId, LocalDate fecha) {
        return asistenciaDAO.registrarSalida(usuarioId, fecha, LocalTime.now());
    }

    public Asistencia obtenerRegistroHoy(int usuarioId, LocalDate fecha) {
        return asistenciaDAO.obtenerPorUsuarioYFecha(usuarioId, fecha);
    }

    public boolean esAtrasado(LocalTime horaEntrada) {
        if (horaEntrada == null) return false;
        return horaEntrada.isAfter(LIMITE_ATRASO);
    }

    public boolean esSalidaAnticipada(LocalTime horaSalida) {
        if (horaSalida == null) return false;
        return horaSalida.isBefore(LIMITE_SALIDA);
    }

    public List<Usuario> obtenerInasistenciasDelDia(LocalDate fecha) {
        return asistenciaDAO.listarInasistencias(fecha);
    }

    public List<Asistencia> obtenerAtrasosDelDia(LocalDate fecha) {
        return asistenciaDAO.listarAtrasos(fecha, LIMITE_ATRASO);
    }

    public List<Asistencia> obtenerSalidasAnticipadasDelDia(LocalDate fecha) {
        return asistenciaDAO.listarSalidasAnticipadas(fecha, LIMITE_SALIDA);
    }
}