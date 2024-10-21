package com.example.colombina.controllers;

import java.util.List;

import com.example.colombina.services.ProgresoService;
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
import com.example.colombina.model.Tramite;
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
    
    //HU-43 - Elimina un tramite que este incompleto
    //Rol que utiliza el metodo: ASUNTOSREG (Agente de la Agencia de Asuntos Regulatorios)
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

    //HU-39 - Filtrar tramites por estado
    //Rol que utiliza el metodo: SOLICITANTE
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

    // Metodo para manejar la revisión de documentos (aceptar/rechazar revisión)
    @CrossOrigin
    @PostMapping("/{idTramite}/revisar")
    public ResponseEntity<?> revisarDocumentos(
            @PathVariable Long idTramite,
            @RequestParam("accion") String accion) {
        try {
            // Verifica si la acción es "aceptar" o "rechazar"
            if (accion.equalsIgnoreCase("aceptar")) {
                // Si la revisión es aceptada, actualizar progreso a 24%
                progresoService.actualizarProgreso(idTramite, 24.0); // Aumentar el progreso a 24%
                return ResponseEntity.ok("Revisión aceptada, el progreso ha sido actualizado.");
            } else if (accion.equalsIgnoreCase("rechazar")) {
                // Si la revisión es rechazada, se puede manejar de otra manera
                // Por ejemplo, se puede regresar el trámite a un estado anterior
                return ResponseEntity.ok("Revisión rechazada, no se actualizó el progreso.");
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
            // Llama al servicio para consolidar el trámite
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
