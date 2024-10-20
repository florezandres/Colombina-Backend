package com.example.colombina.controllers;

import com.example.colombina.DTOs.EstadisticasDTO;
import com.example.colombina.services.NotificacionService;
import com.example.colombina.services.TramiteService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tramites")
public class TramiteController {
    @Autowired
    private TramiteService tramiteService;

    // Apertura de un trámite por su ID -> ASUNTOS REGULATORIOS
    @CrossOrigin
    @PostMapping("/{idTramite}/apertura")
    public ResponseEntity<?> abrirTramite(@PathVariable Long idTramite) {
        try {
            // Llamar al servicio para abrir el trámite
            tramiteService.abrirTramite(idTramite);
            return ResponseEntity.ok("Trámite abierto correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Error si el trámite no se encuentra
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al abrir el trámite.");
        }
    }

    // Obtener estadísticas de trámites nacionales agrupados por mes
    @CrossOrigin
    @GetMapping("/estadisticas/nacionales")
    public ResponseEntity<List<EstadisticasDTO>> obtenerTramitesNacionalesPorMes() {
        List<EstadisticasDTO> estadisticas = tramiteService.obtenerTramitesNacionalesPorMes();
        return ResponseEntity.ok(estadisticas);
    }

    // Obtener estadísticas de trámites internacionales agrupados por mes
    @CrossOrigin
    @GetMapping("/estadisticas/internacionales")
    public ResponseEntity<List<EstadisticasDTO>> obtenerTramitesInternacionalesPorMes() {
        List<EstadisticasDTO> estadisticas = tramiteService.obtenerTramitesInternacionalesPorMes();
        return ResponseEntity.ok(estadisticas);
    }

    // Obtener estadísticas de documentos devueltos agrupados por tipo de documento
    @CrossOrigin
    @GetMapping("/estadisticas/documentos-devueltos")
    public ResponseEntity<List<EstadisticasDTO>> obtenerDocumentosDevueltosPorTipo() {
        List<EstadisticasDTO> estadisticas = tramiteService.obtenerDocumentosDevueltosPorTipo();
        return ResponseEntity.ok(estadisticas);
    }
    //Estadisticas de tramite por periodo
    @GetMapping("/estadisticas/tramites")
    public ResponseEntity<List<EstadisticasDTO>> obtenerTramitesPorPeriodo(@RequestParam String tipo, @RequestParam String periodo) {
        List<EstadisticasDTO> estadisticas = tramiteService.obtenerTramitesPorPeriodo(tipo, periodo);
        return ResponseEntity.ok(estadisticas);
    }
    

    //Filtros por fecha, tipo de trámite y país
    @GetMapping("/estadisticas/filtrados")
    public ResponseEntity<List<EstadisticasDTO>> obtenerTramitesFiltrados(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String pais,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        List<EstadisticasDTO> tramitesFiltrados = tramiteService.obtenerTramitesFiltrados(tipo, pais, fechaInicio, fechaFin);
        return ResponseEntity.ok(tramitesFiltrados);
    }

    //HU 17
    @PostMapping("/{idTramite}/escalar")
    public ResponseEntity<?> escalarTramite(@PathVariable Long idTramite) {
        try {
            // Escalar el trámite y crear la notificación
            tramiteService.escalarTramite(idTramite);
            return ResponseEntity.ok("El trámite ha sido escalado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al escalar el trámite.");
        }
    }



}
