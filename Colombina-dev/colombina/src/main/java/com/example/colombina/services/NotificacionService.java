package com.example.colombina.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.colombina.model.Notificacion;
import com.example.colombina.model.Usuario;
import com.example.colombina.repositories.NotificacionRepository;
import com.example.colombina.repositories.UsuarioRepository;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

@Service
public class NotificacionService {

    @Autowired
    private Resend resendClient;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final String emailFrom = "Colombina <colombina@santicm.com>";

    // Método para enviar una notificación básica por correo
    public void enviarNotificacion(String mensaje) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(emailFrom)
                .to("correo@prueba.com")
                .subject("it works!")
                .html("<strong>" + mensaje + "</strong>")
                .build();

        try {
            CreateEmailResponse data = resendClient.emails().send(params);
            System.out.println("Correo enviado con éxito, ID de la notificación: " + data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    // Método sobrecargado para enviar notificación a un destinatario específico
    public void enviarNotificacion(String destinatarioCorreo, String asunto, String mensaje) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(emailFrom)
                .to(destinatarioCorreo)
                .subject(asunto)
                .html("<strong>" + mensaje + "</strong>")
                .build();

        try {
            CreateEmailResponse data = resendClient.emails().send(params);
            System.out.println(
                    "Correo enviado con éxito a " + destinatarioCorreo + ", ID de la notificación: " + data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    // Método para enviar notificación de validación de documentos a un solicitante
    // por trámite
    public void enviarNotificacion(Long tramiteId, String asunto, String mensaje) {
        Usuario destinatario = usuarioRepository.findSolicitanteByTramiteId(tramiteId);

        if (destinatario != null && destinatario.getCorreoElectronico() != null) {
            // Enviar el correo al solicitante
            enviarNotificacion(destinatario.getCorreoElectronico(), asunto, mensaje);

            // Registrar la notificación en la base de datos
            registrarNotificacion(destinatario, asunto, mensaje);
        } else {
            System.out.println(
                    "No se encontró el solicitante o no tiene un correo registrado para el trámite ID: " + tramiteId);
        }
    }

    // Método para registrar una notificación en la base de datos
    private void registrarNotificacion(Usuario destinatario, String titulo, String mensaje) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(titulo + ": " + mensaje);
        notificacion.setFecha(new Date());
        notificacion.setDestinatario(destinatario);

        notificacionRepository.save(notificacion);
        System.out.println(
                "Notificación registrada en la base de datos para el usuario: " + destinatario.getCorreoElectronico());
    }

    public void recuperarContrasena(String nombre) {
        List<String> admins = getAdminEmails();

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(emailFrom)
                .to(admins)
                .subject("Solicitud para recuperar contraseña")
                .html("<strong>Se solicita al administrador comunicarse con el usuario " + nombre
                        + " para enviarle la nueva contraseña</strong>")
                .build();

        try {
            CreateEmailResponse data = resendClient.emails().send(params);
            System.out.println("Correo enviado con éxito, ID de la notificación: " + data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    private List<String> getAdminEmails() {
        return usuarioRepository.findByRolTipoRol("ADMIN").stream().map(u -> u.getCorreoElectronico()).toList();
    }
}
