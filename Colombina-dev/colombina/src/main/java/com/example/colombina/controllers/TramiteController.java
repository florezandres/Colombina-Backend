package com.example.colombina.controllers;

import java.util.List;

import com.example.colombina.DTOs.ComentarioDTO;
import com.example.colombina.services.ProgresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.Seguimiento;
import com.example.colombina.model.Tramite;
import com.example.colombina.repositories.SeguimientoRepository;
import com.example.colombina.services.DocumentoService;
import com.example.colombina.services.NotificacionService;
import com.example.colombina.services.TramiteService;

@RestController
@RequestMapping("/tramites")
public class TramiteController {

    @Autowired
    private TramiteService tramiteService;

    @Autowired
    private ProgresoService progresoService;

    // Apertura de un trámite por su ID -> ASUNTOS REGULATORIOS
    @CrossOrigin
    @PostMapping("/{idTramite}/apertura")
    public ResponseEntity<?> abrirTramite(@PathVariable Long idTramite) {
        try {
            // Llamar al servicio para abrir el trámite
            tramiteService.abrirTramite(idTramite);
            progresoService.actualizarProgreso(idTramite, 13.0);

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

    @CrossOrigin
    @PostMapping("/{idTramite}/revisar")
    public ResponseEntity<?> revisarDocumentos(
            @PathVariable Long idTramite,
            @RequestParam("accion") String accion,
            @RequestBody(required = false) ComentarioDTO comentarioDTO) {
        try {
            if (accion.equalsIgnoreCase("aceptar")) {
                // Aceptar revisión, actualizar progreso a 24%
                progresoService.actualizarProgreso(idTramite, 24.0);
                return ResponseEntity.ok("Revisión aceptada, el progreso ha sido actualizado.");
            } else if (accion.equalsIgnoreCase("rechazar")) {
                if (comentarioDTO == null || comentarioDTO.getComentario().isEmpty()) {
                    return ResponseEntity.status(400).body("Debe proporcionar un comentario al rechazar.");
                }

                // Actualizar progreso a 13% (retrocediendo a un estado anterior)
                progresoService.actualizarProgreso(idTramite, 13.0);

                // Guardar el comentario en el historial de cambios
                tramiteService.agregarComentarioAlHistorial(idTramite, comentarioDTO, "Revisión rechazada");

                return ResponseEntity.ok("Revisión rechazada con comentario.");
            } else {
                return ResponseEntity.status(400).body("Acción inválida. Use 'aceptar' o 'rechazar'.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al revisar los documentos.");
        }
    }


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
            progresoService.actualizarProgreso(idTramite, 42.0);

            return ResponseEntity.ok("Consolidación completada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al consolidar el trámite.");
        }
    }
}
