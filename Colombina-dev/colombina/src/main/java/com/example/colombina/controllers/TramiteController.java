package com.example.colombina.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.Seguimiento;
import com.example.colombina.model.Tramite;
import com.example.colombina.repositories.SeguimientoRepository;
import com.example.colombina.services.DocumentoService;
import com.example.colombina.services.NotificacionService;
import com.example.colombina.services.TramiteService;
import com.example.colombina.services.NotificacionService;

@RestController
@RequestMapping("/tramites")
public class TramiteController {

    @Autowired
    private TramiteService tramiteService;

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private NotificacionService notificacionService;

    // Validación automática de documentos
    @GetMapping("/{idTramite}/validar-documentos")
    public ResponseEntity<?> validarDocumentos(@PathVariable Long idTramite) {
        try {
            documentoService.validarDocumentos(idTramite);
            return ResponseEntity.ok("Validación de documentos completada.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en la validación de documentos.");
        }
    }

    // Apertura de un trámite por su ID -> ASUNTOS REGULATORIOS
    @CrossOrigin
    @PostMapping("/{idTramite}/apertura")
    public ResponseEntity<?> abrirTramite(@PathVariable Long idTramite) {
        try {
            // Llamar al servicio para abrir el trámite
            tramiteService.abrirTramite(idTramite);
            notificacionService.enviarNotificacionEstadoTramite(idTramite); // Enviar notificación de cambio de estado
            return ResponseEntity.ok("Trámite abierto correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Error si el trámite no se encuentra
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al abrir el trámite.");
        }
    }

    // HU-43 - Elimina un trámite que esté incompleto
    // Rol que utiliza el método: ASUNTOSREG (Agente de la Agencia de Asuntos Regulatorios)
    @DeleteMapping("/{idTramite}/eliminar")
    public ResponseEntity<?> eliminarTramite(@PathVariable Long idTramite) {
        try {
            tramiteService.eliminarTramite(idTramite);
            return ResponseEntity.ok("Trámite eliminado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar el trámite.");
        }
    }

    // HU-39 - Filtrar trámites por estado
    // Rol que utiliza el método: SOLICITANTE
    @CrossOrigin
    @GetMapping("/filtrado/estado")
    public ResponseEntity<?> filtrarTramitesPorEstado(@RequestParam Tramite.EstadoTramite estado) {
        try {
            List<TramiteDTO> tramitesFiltrados = tramiteService.filtrarTramitesPorEstado(estado);
            return ResponseEntity.ok(tramitesFiltrados);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al filtrar los trámites.");
        }
    }

    // HU-13 - Obtener seguimiento de un trámite
    @GetMapping("/{idTramite}/seguimiento")
    public ResponseEntity<?> obtenerSeguimiento(@PathVariable Long idTramite) {
        List<Seguimiento> seguimientos = seguimientoRepository.findByTramiteId(idTramite);
        return ResponseEntity.ok(seguimientos);
    }

    // HU-46 - Validación Automática de Documentos y Consolidación
    @CrossOrigin
    @PostMapping("/{idTramite}/consolidacion")
    public ResponseEntity<?> consolidarTramite(@PathVariable Long idTramite) {
        try {
            // Validación automática de documentos antes de la consolidación
            boolean validacionExitosa = documentoService.validarDocumentos(idTramite);

            if (!validacionExitosa) {
                return ResponseEntity.status(400).body("Existen documentos faltantes o incorrectos. Por favor, corrija los documentos antes de continuar.");
            }

            // Si los documentos son válidos, proceder con la consolidación del trámite
            tramiteService.consolidarTramite(idTramite);
            return ResponseEntity.ok("Consolidación completada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al consolidar el trámite.");
        }
    }

}
