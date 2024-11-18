package com.example.colombina.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/usuario")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorUsuario(Principal principal) {

    List<Notificacion> notificaciones = notificacionService.obtenerNotificacionesPorUsuario(principal.getName());

    if (notificaciones.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    return ResponseEntity.status(HttpStatus.OK).body(notificaciones);
    }

    @GetMapping("/usuario/paginacion")
    public ResponseEntity<Page<Notificacion>> obtenerNotificacionesPorUsuarioConPaginacion(
            Principal principal,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Notificacion> notificaciones = notificacionService.obtenerNotificacionesPorUsuarioConPaginacion(principal.getName(), page, size);

        if (notificaciones.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(notificaciones);
    }

    @PostMapping("/marcarLeida/{notificacionId}")
    public ResponseEntity<Map<String, String>> marcarNotificacionComoLeida(@PathVariable Long notificacionId) {
        notificacionService.marcarNotificacionComoLeida(notificacionId);
        Map<String, String> response = Map.of("mensaje", "Notificación marcada como leída");
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
