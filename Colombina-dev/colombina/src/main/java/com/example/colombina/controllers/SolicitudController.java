package com.example.colombina.controllers;

import com.example.colombina.DTOs.RequestTramiteSolicitudDTO;
import com.example.colombina.DTOs.SolicitudDTO;
import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.services.ProgresoService;
import com.example.colombina.services.SolicitudService;

import lombok.extern.slf4j.Slf4j;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private ProgresoService progresoService;

    // Crear solicitud y trámite -> SOLICITANTE DEI
    @CrossOrigin
    @PostMapping(value = "/crear-solicitud", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearSolicitud(
            @RequestBody RequestTramiteSolicitudDTO requestTramiteSolicitudDTO,
            Principal principal) {
        log.info("Creando solicitud...");
        System.out.println("Creando solicitud...");

        String username = principal.getName();

        try {
            // Extraer la solicitud y el trámite por separados
            SolicitudDTO solicitudDTO = requestTramiteSolicitudDTO.getSolicitudDTO();
            TramiteDTO tramiteDTO = requestTramiteSolicitudDTO.getTramiteDTO();
            // Llamada al servicio para crear la solicitud
            SolicitudDTO nuevaSolicitud = solicitudService.crearSolicitud(solicitudDTO, tramiteDTO, username);

            // Actualizar el progreso en 11% para el primer paso
            progresoService.actualizarProgreso(tramiteDTO.getId(), 0);
            return ResponseEntity.ok(nuevaSolicitud);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Error en caso de duplicado o validaciones
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error procesando la solicitud.");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getSolicitudesPorSolicitante(Principal principal) {
        log.info(principal.getName());
        return ResponseEntity.ok().body(solicitudService.getSolicitudesPorSolicitante(principal.getName()));
    }

    @GetMapping(value = "/todos")
    public ResponseEntity<?> findAll(Principal principal) {
        log.info(principal.getName());
        return ResponseEntity.ok().body(solicitudService.getSolicitudes(principal.getName()));
    }

}
