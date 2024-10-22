package com.example.colombina.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.colombina.services.NotificacionService;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

     // Notificación automática cuando el estado del trámite cambia
    @PostMapping("/estadoTramite/{tramiteId}")
    public String notificarEstadoTramite(@PathVariable Long tramiteId) {
        notificacionService.enviarNotificacionEstadoTramite(tramiteId);
        return "Notificación de estado de trámite enviada";
    }

    // Notificación de documentos faltantes
    @PostMapping("/documentosFaltantes/{tramiteId}")
    public String notificarDocumentosFaltantes(@PathVariable Long tramiteId) {
        notificacionService.enviarNotificacionDocumentosFaltantes(tramiteId);
        return "Notificación de documentos faltantes enviada";
    }

    // Notificación de expiración de trámite
    @PostMapping("/expiracionTramite/{tramiteId}")
    public String notificarExpiracionTramite(@PathVariable Long tramiteId) {
        notificacionService.enviarNotificacionExpiracionTramite(tramiteId);
        return "Notificación de expiración de trámite enviada";
    }
}
    
