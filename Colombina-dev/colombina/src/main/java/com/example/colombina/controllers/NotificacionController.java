package com.example.colombina.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.colombina.model.Notificacion;
import com.example.colombina.services.NotificacionService;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // Notificación automática cuando el estado del trámite cambia
    @PostMapping("/estadoTramite/{tramiteId}/{nuevoEstado}")
    public ResponseEntity<String> notificarEstadoTramite(@PathVariable Long tramiteId, @PathVariable String nuevoEstado) {
        notificacionService.enviarNotificacionEstadoTramite(tramiteId, nuevoEstado);
        return ResponseEntity.status(HttpStatus.OK).body("Notificación de estado de trámite enviada");
    }

    @PostMapping("/documentosFaltantes/{tramiteId}")
    public ResponseEntity<String> notificarDocumentosFaltantes(@PathVariable Long tramiteId, @RequestBody List<String> documentosFaltantes) {
        notificacionService.enviarNotificacionDocumentosFaltantes(tramiteId, documentosFaltantes);
        return ResponseEntity.status(HttpStatus.OK).body("Notificación de documentos faltantes enviada");
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorUsuario(@PathVariable Long usuarioId) {
        List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesPorUsuario(usuarioId);
        if (notificaciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(notificaciones);
    }

    @PostMapping("/marcarLeida/{notificacionId}")
    public ResponseEntity<String> marcarNotificacionComoLeida(@PathVariable Long notificacionId) {
        notificacionService.marcarNotificacionComoLeida(notificacionId);
        return ResponseEntity.status(HttpStatus.OK).body("Notificación marcada como leída");
    }

    // Notificación de expiración de trámite
    @PostMapping("/expiracionTramite/{tramiteId}")
    public ResponseEntity<String> notificarExpiracionTramite(@PathVariable Long tramiteId) {
        notificacionService.enviarNotificacionExpiracionTramite(tramiteId);
        return ResponseEntity.status(HttpStatus.OK).body("Notificación de expiración de trámite enviada");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Notificacion>> obtenerTodasLasNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.obtenerTodasLasNotificaciones();
        return ResponseEntity.status(HttpStatus.OK).body(notificaciones);
    }

    @GetMapping("/{id}")
    public Notificacion obtenerNotificacionPorId(@PathVariable("id") Long id) {
        return notificacionService.obtenerNotificacionPorId(id);
    }

}
