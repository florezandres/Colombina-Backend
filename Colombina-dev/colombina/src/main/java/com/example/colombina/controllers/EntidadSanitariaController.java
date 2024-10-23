package com.example.colombina.controllers;

import com.example.colombina.DTOs.ComentarioDTO;
import com.example.colombina.model.Tramite;
import com.example.colombina.services.ProgresoService;
import com.example.colombina.services.TramiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entidadSanitaria")
public class EntidadSanitariaController {

    @Autowired
    ProgresoService progresoService;

    @Autowired
    TramiteService tramiteService;

    //Paso 5
    @CrossOrigin
    @PostMapping("/tramite/{idTramite}/aceptar")
    public ResponseEntity<?> aceptarTramite(
            @PathVariable Long idTramite,
            @RequestParam("numeroRadicado") String numeroRadicado,
            @RequestParam("llave") Double llave) {
        try {
            if (numeroRadicado == null || llave == null) {
                return ResponseEntity.status(400).body("Número de radicado y llave son requeridos.");
            }



            // Llamar al servicio para actualizar el trámite con el número de radicado y la llave
            tramiteService.asociarNumeroRadicadoYLLave(idTramite, numeroRadicado, llave);
            progresoService.actualizarProgreso(idTramite, 61.0);

            return ResponseEntity.ok("Trámite aceptado y número de radicado asignado.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al aceptar el trámite.");
        }
    }

    //Paso 5
    @CrossOrigin
    @PostMapping("/tramite/{idTramite}/rechazar")
    public ResponseEntity<?> rechazarTramite(
            @PathVariable Long idTramite,
            @RequestBody ComentarioDTO comentarioDTO) {
        try {
            if (comentarioDTO == null || comentarioDTO.getComentario().isEmpty()) {
                return ResponseEntity.status(400).body("Debe proporcionar un comentario para rechazar el trámite.");
            }

            // Llamar al servicio para agregar el comentario al historial de cambios
            tramiteService.agregarComentarioAlHistorial(idTramite, comentarioDTO, "Trámite rechazado");
            progresoService.actualizarProgreso(idTramite, 42.0);


            return ResponseEntity.ok("Trámite rechazado con comentario agregado al historial.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al rechazar el trámite.");
        }
    }

    //Paso 5
    @CrossOrigin
    @PostMapping("/tramite/{idTramite}/opcionesRechazo")
    public ResponseEntity<?> opcionesRechazoTramite(
            @PathVariable Long idTramite,
            @RequestParam("accion") String accion,
            @RequestBody ComentarioDTO comentarioDTO) {
        try {
            if (comentarioDTO == null || comentarioDTO.getComentario().isEmpty()) {
                return ResponseEntity.status(400).body("Debe proporcionar un comentario para esta acción.");
            }

            String descripcionCambio = "";
            if (accion.equalsIgnoreCase("arreglar")) {
                descripcionCambio = "Trámite en proceso de arreglar";
            } else if (accion.equalsIgnoreCase("enviar_a_solicitante")) {
                descripcionCambio = "Trámite enviado al solicitante";
            } else {
                return ResponseEntity.status(400).body("Acción inválida. Use 'arreglar' o 'enviar_a_solicitante'.");
            }

            // Llamar al servicio para agregar el comentario con la acción correspondiente
            tramiteService.agregarComentarioAlHistorial(idTramite, comentarioDTO, descripcionCambio);
            progresoService.actualizarProgreso(idTramite, 42.0);


            return ResponseEntity.ok("Acción realizada con comentario agregado al historial.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al realizar la acción sobre el trámite.");
        }
    }






}
