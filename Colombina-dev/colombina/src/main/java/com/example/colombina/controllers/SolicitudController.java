package com.example.colombina.controllers;

import com.example.colombina.DTOs.RequestTramiteSolicitudDTO;
import com.example.colombina.DTOs.SolicitudDTO;
import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.services.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    // Crear solicitud y trámite -> SOLICITANTE DEI
    @CrossOrigin
    @PostMapping(value = "/{idUsuario}/{idEntidad}/crear-solicitud", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearSolicitud(
            @RequestBody RequestTramiteSolicitudDTO requestTramiteSolicitudDTO,
            @PathVariable Long idUsuario,
            @PathVariable Long idEntidad) {
        System.out.println("Creando solicitud...");

        try {
            //Extraer la solicitud y el trámite por separados
            SolicitudDTO solicitudDTO = requestTramiteSolicitudDTO.getSolicitudDTO();
            TramiteDTO tramiteDTO = requestTramiteSolicitudDTO.getTramiteDTO();
            // Llamada al servicio para crear la solicitud
            SolicitudDTO nuevaSolicitud = solicitudService.crearSolicitud(solicitudDTO, tramiteDTO, idUsuario, idEntidad);
            return ResponseEntity.ok(nuevaSolicitud);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Error en caso de duplicado o validaciones
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error procesando la solicitud.");
        }
    }
}
