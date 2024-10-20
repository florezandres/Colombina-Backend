package com.example.colombina.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.colombina.model.Notificacion;
import com.example.colombina.repositories.NotificacionRepository;

@Service
public class NotificacionService {    

    @Autowired
    private NotificacionRepository notificacionRepository;
    
     public void crearNotificacion(Notificacion notificacion) {
        notificacionRepository.save(notificacion);
    }
    
}
