package com.example.colombina.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.colombina.services.NotificacionService;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @GetMapping
    public String notificar() {
        notificacionService.enviarNotificacion("Hola, esto es una notificación");
        return "Notificación enviada";
    }
    
}
