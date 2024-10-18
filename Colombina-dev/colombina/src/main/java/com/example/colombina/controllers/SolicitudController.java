package com.example.colombina.controllers;

import com.example.colombina.DTOs.SolicitudDTO;
import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.services.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solitudes")
public class SolicitudController {
    @Autowired
    private SolicitudService solicitudService;

    // Crear solicitud y trámite -> SOLICITANTE DEI
    @CrossOrigin
    @PostMapping(value = "/{idUsuario}/crear-solicitud", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearSolicitud(
            @RequestBody SolicitudDTO solicitudDTO,
            @RequestBody TramiteDTO tramiteDTO,
            @PathVariable Long idUsuario) {

        try {
            // Llamada al servicio para crear la solicitud
            SolicitudDTO nuevaSolicitud = solicitudService.crearSolicitud(solicitudDTO, tramiteDTO, idUsuario);
            return ResponseEntity.ok(nuevaSolicitud);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Error en caso de duplicado o validaciones
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error procesando la solicitud.");
        }
    }
}
