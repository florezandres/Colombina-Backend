package com.example.colombina.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public String notificarEstadoTramite(@PathVariable Long tramiteId, @PathVariable String nuevoEstado) {
        notificacionService.enviarNotificacionEstadoTramite(tramiteId, nuevoEstado);
        return "Notificación de estado de trámite enviada";
    }

    @PostMapping("/documentosFaltantes/{tramiteId}")
    public String notificarDocumentosFaltantes(@PathVariable Long tramiteId, @RequestBody List<String> documentosFaltantes) {
        notificacionService.enviarNotificacionDocumentosFaltantes(tramiteId, documentosFaltantes);
        return "Notificación de documentos faltantes enviada";
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Notificacion> obtenerNotificacionesPorUsuario(@PathVariable Long usuarioId) {
        return notificacionService.obtenerNotificacionesPorUsuario(usuarioId);
    }

    @PostMapping("/marcarLeida/{notificacionId}")
    public String marcarNotificacionComoLeida(@PathVariable Long notificacionId) {
        notificacionService.marcarNotificacionComoLeida(notificacionId);
        return "Notificación marcada como leída";
    }

    // Notificación de expiración de trámite
    @PostMapping("/expiracionTramite/{tramiteId}")
    public String notificarExpiracionTramite(@PathVariable Long tramiteId) {
        notificacionService.enviarNotificacionExpiracionTramite(tramiteId);
        return "Notificación de expiración de trámite enviada";
    }

    @GetMapping("/all")
    public List<Notificacion> obtenerTodasLasNotificaciones() {
        return notificacionService.obtenerTodasLasNotificaciones();
    }


}
    
