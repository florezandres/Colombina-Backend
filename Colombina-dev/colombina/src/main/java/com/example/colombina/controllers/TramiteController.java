package com.example.colombina.controllers;

import com.example.colombina.DTOs.SolicitudDTO;
import com.example.colombina.model.Solicitud;
import com.example.colombina.model.TramiteRegulatorio;
import com.example.colombina.services.TramiteRegulatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tramites")
public class TramiteController {
    @Autowired
    private TramiteRegulatorioService tramiteRegulatorioService;

    @PostMapping("/crear-solicitud")
    public ResponseEntity<?> crearSolicitud(
            @RequestPart("solicitud") SolicitudDTO solicitud,
            @RequestPart("documentos") List<MultipartFile> documentos) {
        try {
            TramiteRegulatorio tramite = tramiteRegulatorioService.crearSolicitudTramite(solicitud, documentos);
            return ResponseEntity.ok("Solicitud generada correctamente. Id de solicitud: " + solicitud.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}
