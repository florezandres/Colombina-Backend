package com.example.colombina.controllers;

import com.example.colombina.DTOs.ReporteDTO;
import com.example.colombina.model.Tramite;
import com.example.colombina.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/generar")
    public ResponseEntity<?> generarReportes(
            @RequestParam Date fechaInicio,
            @RequestParam Date fechaFin,
            @RequestParam String tipoTramite,
            @RequestParam Tramite.EstadoTramite estado,
            @RequestParam String formato) {

        try {
            // Generar datos de reporte
            List<ReporteDTO> reportes = reporteService.generarReporteDatos(fechaInicio, fechaFin, tipoTramite, estado);

            if ("pdf".equalsIgnoreCase(formato)) {
                // Generar archivo PDF
                ByteArrayInputStream pdfFile = reporteService.generarReportePDF(reportes,
                        (double) reportes.stream().filter(r -> r.getEstado().equals("APROBADO")).count() / reportes.size() * 100,
                        (double) reportes.stream().filter(r -> r.getEstado().equals("RECHAZADO")).count() / reportes.size() * 100);

                // Configurar encabezados para PDF
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment; filename=reportes.pdf");
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdfFile);
            } else if ("excel".equalsIgnoreCase(formato)) {
                // Generar archivo Excel
                ByteArrayInputStream excelFile = reporteService.generarReporteExcel(reportes);

                // Configurar encabezados para Excel
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment; filename=reportes.xlsx");
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(excelFile);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato no soportado. Use 'pdf' o 'excel'.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generando reportes");
        }
    }
}
