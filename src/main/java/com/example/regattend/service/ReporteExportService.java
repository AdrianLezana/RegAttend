package com.example.regattend.service;

import com.example.regattend.model.entity.ReporteItem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ReporteExportService {

    public static boolean exportarAExcel(List<ReporteItem> datos, File archivoDestino) {
        // Hacemos try-with-resources para asegurar que el archivo se cierre al terminar
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte Asistencia");

            // 1. Estilo para la fila de Encabezados (Fondo gris y letra en Negrita)
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // 2. Crear la fila 0 (Encabezados)
            Row headerRow = sheet.createRow(0);
            String[] columnas = {"Nombre Empleado", "Fecha", "Hora", "Detalle"};

            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
                cell.setCellStyle(headerStyle);
            }

            // 3. Llenar los datos de la tabla (A partir de la fila 1)
            int rowNum = 1;
            for (ReporteItem item : datos) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(item.getNombre());
                row.createCell(1).setCellValue(item.getFecha());
                row.createCell(2).setCellValue(item.getHora());
                row.createCell(3).setCellValue(item.getDetalle());
            }

            // 4. Auto-ajustar el ancho de las columnas para que se lea todo
            for (int i = 0; i < columnas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // 5. Escribir el archivo físico en el disco del usuario
            try (FileOutputStream fileOut = new FileOutputStream(archivoDestino)) {
                workbook.write(fileOut);
            }

            return true;
        } catch (IOException e) {
            System.err.println("Error al exportar a Excel: " + e.getMessage());
            return false;
        }
    }
}