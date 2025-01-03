package com.example.colombina.controllers;

import com.example.colombina.DTOs.RequestTramiteSolicitudDTO;
import com.example.colombina.DTOs.SolicitudDTO;
import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.Tramite;
import com.example.colombina.services.SolicitudService;

import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @PostMapping(value = "/crear-solicitud", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crearSolicitud(
            @RequestBody RequestTramiteSolicitudDTO requestTramiteSolicitudDTO,
            Principal principal) {
        log.info("Creando solicitud...");

        String username = principal.getName();

        try {
            // Extraer la solicitud y el trámite por separados
            SolicitudDTO solicitudDTO = requestTramiteSolicitudDTO.getSolicitudDTO();
            TramiteDTO tramiteDTO = requestTramiteSolicitudDTO.getTramiteDTO();
            // Llamada al servicio para crear la solicitud
            SolicitudDTO nuevaSolicitud = solicitudService.crearSolicitud(solicitudDTO, tramiteDTO, username);

            return ResponseEntity.ok(nuevaSolicitud);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Error en caso de duplicado o validaciones
        }
    }

    @GetMapping(value = "tramite/{id}")
    public ResponseEntity<?> getTramite(@PathVariable Long id) {
        return ResponseEntity.ok().body(solicitudService.getSolicitudByTramite(id));
    }

    @GetMapping()
    public ResponseEntity<?> getSolicitudesPorSolicitante(Principal principal,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer limit) {
        log.info(principal.getName());
        return ResponseEntity.ok()
                .body(solicitudService.getSolicitudesPorSolicitante(principal.getName(), page, limit));
    }

    @GetMapping(value = "/filtros")
    public ResponseEntity<?> findByFiltersAndSolicitante(Principal principal,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer limit,
            @RequestParam(required = false) Tramite.EstadoTramite estado,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Tramite.TipoTramite nacionalidad,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaFin,
            @RequestParam(required = false) String filtro) {
        return ResponseEntity.ok().body(solicitudService.findByFiltersAndSolicitante(principal.getName(),page, limit, estado, tipo, nacionalidad,
                fechaInicio, fechaFin, filtro));
    }

    @GetMapping(value = "/todos")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok().body(solicitudService.getSolicitudes(page, limit));
    }

    @GetMapping(value = "/todos/filtros")
    public ResponseEntity<?> findByFilters(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer limit,
            @RequestParam(required = false) Tramite.EstadoTramite estado,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Tramite.TipoTramite nacionalidad,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaFin,
            @RequestParam(required = false) String filtro) {
        return ResponseEntity.ok().body(solicitudService.findByFilters(page, limit, estado, tipo, nacionalidad,
                fechaInicio, fechaFin, filtro));
    }

}
