package com.example.regattend.service;

import com.example.regattend.model.entity.Asistencia;
import com.example.regattend.model.entity.Usuario;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ReporteExportService {

    public static boolean exportarInasistenciasCsv(File archivo, List<Usuario> inasistentes) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8))) {
            pw.println("ID,Nombre,Correo,Rol");
            for (Usuario u : inasistentes) {
                pw.println(u.getId() + "," + escapeCsv(u.getNombre()) + "," + escapeCsv(u.getCorreo()) + "," + u.getRol());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean exportarAsistenciasCsv(File archivo, List<Asistencia> lista) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8))) {
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

    private static String escapeCsv(String valor) {
        if (valor == null) return "";
        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }
}