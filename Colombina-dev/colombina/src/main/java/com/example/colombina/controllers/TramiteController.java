package com.example.colombina.controllers;

import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.services.TramiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
}
