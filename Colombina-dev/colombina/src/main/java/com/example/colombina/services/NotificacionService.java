package com.example.colombina.services;

import org.springframework.stereotype.Service;

import com.example.colombina.model.Notificacion;
import com.example.colombina.model.Usuario;
import com.example.colombina.repositories.NotificacionRepository;
import com.example.colombina.repositories.UsuarioRepository;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class NotificacionService {
    @Autowired
    private Resend resendClient;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void enviarNotificacion(String mensaje) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Colombina <colombina@resend.dev>")
                .to("correo@prueba.com")
                .subject("it works!")
                .html("<strong>" + mensaje + "</strong>")
                .build();

         try {
            CreateEmailResponse data = resendClient.emails().send(params);
            System.out.println(data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
        }
    }

    public void enviarNotificacion(Long tramiteId, String titulo, String mensaje) {
        // Asumimos que obtenemos el solicitante del trámite
        Usuario destinatario = usuarioRepository.findSolicitanteByTramiteId(tramiteId);

        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(titulo + ": " + mensaje);
        notificacion.setFecha(new Date());
        notificacion.setDestinatario(destinatario);

        notificacionRepository.save(notificacion);
    } 


}
