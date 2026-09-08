package com.example.regattend.service;

import com.example.regattend.model.entity.Asistencia;
import com.example.regattend.model.entity.Usuario;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ReporteExportService {

    public static boolean exportarInasistenciasCsv(String rutaArchivo, List<Usuario> inasistentes) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo))) {
            pw.println("ID,Nombre,Correo,Rol");
            for (Usuario u : inasistentes) {
                pw.println(u.getId() + "," + u.getNombre() + "," + u.getCorreo() + "," + u.getRol());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean exportarAsistenciasCsv(String rutaArchivo, List<Asistencia> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo))) {
            pw.println("ID,UsuarioID,Fecha,Hora Entrada,Hora Salida");
            for (Asistencia a : lista) {
                pw.println(a.getId() + "," + a.getUsuarioId() + "," + a.getFecha() + "," +
                        (a.getHoraEntrada() != null ? a.getHoraEntrada() : "") + "," +
                        (a.getHoraSalida() != null ? a.getHoraSalida() : ""));
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}