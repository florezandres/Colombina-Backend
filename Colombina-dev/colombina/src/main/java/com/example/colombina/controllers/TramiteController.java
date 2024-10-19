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
import com.example.colombina.model.Tramite;
import com.example.colombina.services.TramiteService;

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
}
