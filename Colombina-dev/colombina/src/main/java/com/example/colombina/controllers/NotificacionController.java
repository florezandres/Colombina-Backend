package com.example.colombina.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.colombina.services.NotificacionService;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping
    public String notificar() {
        notificacionService.enviarNotificacion("Hola, esto es una notificación");
        return "Notificación enviada";
    }
    
}
