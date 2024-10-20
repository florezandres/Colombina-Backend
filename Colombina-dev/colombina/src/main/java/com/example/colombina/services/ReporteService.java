package com.example.colombina.services;

import com.example.colombina.DTOs.ReporteDTO;
import com.example.colombina.model.Seguimiento;
import com.example.colombina.model.Tramite;
import com.example.colombina.repositories.TramiteRepository;
import com.itextpdf.text.Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ReporteService {

    @Autowired
    private TramiteRepository tramiteRepository;

    /*public List<ReporteDTO> generarReporteDatos(Date fechaInicio, Date fechaFin, String tipoTramite, Tramite.EstadoTramite estado) {
        List<Tramite> tramites = tramiteRepository.findByFechaRadicacionTipoAndEstado(fechaInicio, fechaFin, tipoTramite, estado);
        List<ReporteDTO> reportes = new ArrayList<>();

        int totalTramites = tramites.size();
        int aprobados = 0;
        int rechazados = 0;

        for (Tramite tramite : tramites) {
            ReporteDTO reporteDTO = new ReporteDTO();
            reporteDTO.setNumeroRadicado(tramite.getNumeroRadicado());
            reporteDTO.setEstado(tramite.getEstado().toString());

            // Calcular tiempo de procesamiento
            if (tramite.getEstado() == Tramite.EstadoTramite.APROBADO) {
                Date fechaFinal = obtenerFechaFinalDeTramite(tramite);
                long diasDeProcesamiento = calcularDiasEntreFechas(tramite.getFechaRadicacion(), fechaFinal);
                reporteDTO.setTiempoDeProcesamiento(diasDeProcesamiento + " días");
                aprobados++;
            } else if (tramite.getEstado() == Tramite.EstadoTramite.RECHAZADO) {
                Date fechaFinal = obtenerFechaFinalDeTramite(tramite);
                long diasDeProcesamiento = calcularDiasEntreFechas(tramite.getFechaRadicacion(), fechaFinal);
                reporteDTO.setTiempoDeProcesamiento(diasDeProcesamiento + " días");
                rechazados++;
            } else {
                reporteDTO.setTiempoDeProcesamiento("Trámite no finalizado");
            }

            reportes.add(reporteDTO);
        }

        // Calcular tasas de aprobación y rechazo
        double tasaAprobacion = totalTramites > 0 ? (double) aprobados / totalTramites * 100 : 0;
        double tasaRechazo = totalTramites > 0 ? (double) rechazados / totalTramites * 100 : 0;

        // Agregar las tasas al final del reporte
        ReporteDTO tasasDTO = new ReporteDTO();
        tasasDTO.setNumeroRadicado("Tasas");
        tasasDTO.setTiempoDeProcesamiento(String.format("Aprobación: %.2f%%, Rechazo: %.2f%%", tasaAprobacion, tasaRechazo));
        reportes.add(tasasDTO);

        try {
            ByteArrayInputStream excelFile = generarReporteExcel(reportes);
            ByteArrayInputStream pdfFile = generarReportePDF(reportes, tasaAprobacion, tasaRechazo);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return reportes;
    }
*/

    private long calcularDiasEntreFechas(Date fechaInicio, Date fechaFin) {
        // Convertir Date a LocalDate
        LocalDate startDate = fechaInicio.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate endDate = fechaFin.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Calcular la diferencia en días
        return java.time.Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays();
    }

    private Date obtenerFechaFinalDeTramite(Tramite tramite) {
        List<Seguimiento> seguimientos = tramite.getSeguimientos();
        for (Seguimiento seguimiento : seguimientos) {
            if (seguimiento.getEstadoActual().equalsIgnoreCase("APROBADO") ||
                    seguimiento.getEstadoActual().equalsIgnoreCase("RECHAZADO")) {
                return seguimiento.getFechaSeguimiento();
            }
        }
        return new Date(); // Fecha actual como respaldo
    }

    // Genera reporte en Excel
    public ByteArrayInputStream generarReporteExcel(List<ReporteDTO> reportes) throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte de Trámites");
            Row headerRow = sheet.createRow(0);

            String[] columnas = {"Número Radicado", "Estado", "Tiempo de Procesamiento"};
            for (int i = 0; i < columnas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columnas[i]);
            }

            int rowIdx = 1;
            for (ReporteDTO reporte : reportes) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(reporte.getNumeroRadicado());
                row.createCell(1).setCellValue(reporte.getEstado());
                row.createCell(2).setCellValue(reporte.getTiempoDeProcesamiento());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    // Genera reporte en PDF
    public ByteArrayInputStream generarReportePDF(List<ReporteDTO> reportes, double tasaAprobacion, double tasaRechazo) throws Exception {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        Paragraph title = new Paragraph("Reporte de Trámites", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 3, 3});

        Stream.of("Número Radicado", "Estado", "Tiempo de Procesamiento").forEach(headerTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setPhrase(new Phrase(headerTitle));
            table.addCell(header);
        });

        for (ReporteDTO reporte : reportes) {
            table.addCell(reporte.getNumeroRadicado());
            table.addCell(reporte.getEstado());
            table.addCell(reporte.getTiempoDeProcesamiento());
        }

        document.add(table);

        // Agregar las tasas de aprobación y rechazo al final del PDF
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Tasas de Aprobación: " + String.format("%.2f%%", tasaAprobacion)));
        document.add(new Paragraph("Tasas de Rechazo: " + String.format("%.2f%%", tasaRechazo)));

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

}
