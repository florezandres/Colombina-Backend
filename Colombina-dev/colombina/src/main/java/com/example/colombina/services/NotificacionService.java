package com.example.colombina.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.colombina.model.Notificacion;
import com.example.colombina.model.Usuario;
import com.example.colombina.model.Tramite;
import com.example.colombina.repositories.NotificacionRepository;
import com.example.colombina.repositories.UsuarioRepository;
import com.example.colombina.repositories.TramiteRepository;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private Resend resendClient;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TramiteRepository tramiteRepository;

    public void verificarExpiracionTramites() {
        List<Tramite> tramites = tramiteRepository.findAll();
        Date fechaActual = new Date();
        
        for (Tramite tramite : tramites) {
            // Simulación de la fecha de expiración: 30 días después de la fecha de radicación
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tramite.getFechaRadicacion());
            calendar.add(Calendar.DAY_OF_MONTH, 30); // Establecer la fecha de expiración a 30 días después
            Date fechaExpiracion = calendar.getTime();

            // Verificar si el trámite está próximo a expirar (30 días o menos)
            Calendar calendarLimite = Calendar.getInstance();
            calendarLimite.setTime(fechaExpiracion);
            calendarLimite.add(Calendar.DAY_OF_MONTH, -30); // Resta 30 días para la comparación
            Date fechaLimite = calendarLimite.getTime();

            if (fechaLimite.before(fechaActual)) {
                enviarNotificacionExpiracionTramite(tramite.getId());
            }
    }
}

    // Notificación automática cuando el estado del trámite cambia
    public void enviarNotificacionEstadoTramite(Long tramiteId) {
        // Obtiene el usuario solicitante asociado al trámite
        Usuario destinatario = usuarioRepository.findSolicitanteByTramiteId(tramiteId);
        String mensaje = "Su trámite ha cambiado de estado.";
        
        // Enviar correo electrónico
        enviarCorreo(destinatario.getCorreoElectronico(), "Cambio de estado del trámite", mensaje);
        
        // Crear y guardar notificación en la base de datos
        guardarNotificacion(tramiteId, "Estado de Trámite", mensaje);
    }

    // Notificación de documentos faltantes
    public void enviarNotificacionDocumentosFaltantes(Long tramiteId) {
        Usuario destinatario = usuarioRepository.findSolicitanteByTramiteId(tramiteId);
        String mensaje = "Faltan documentos para su trámite. Por favor, adjúntelos para continuar.";
        
        enviarCorreo(destinatario.getCorreoElectronico(), "Documentos Faltantes", mensaje);
        guardarNotificacion(tramiteId, "Documentos Faltantes", mensaje);
    }

    // Notificación de expiración de trámite
    public void enviarNotificacionExpiracionTramite(Long tramiteId) {
        Usuario destinatario = usuarioRepository.findSolicitanteByTramiteId(tramiteId);
        String mensaje = "Su trámite está a punto de expirar. Renueve su solicitud a tiempo.";
        
        enviarCorreo(destinatario.getCorreoElectronico(), "Expiración de Trámite", mensaje);
        guardarNotificacion(tramiteId, "Expiración de Trámite", mensaje);
    }

    // Método auxiliar para enviar correos
    public void enviarCorreo(String destinatario, String asunto, String mensaje) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Colombina <colombina@resend.dev>")
                .to(destinatario)
                .subject(asunto)
                .html("<strong>" + mensaje + "</strong>")
                .build();

        try {
            CreateEmailResponse data = resendClient.emails().send(params);
            System.out.println("Email enviado: " + data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para guardar la notificación en la base de datos
    public void guardarNotificacion(Long tramiteId, String titulo, String mensaje) {

        Usuario destinatario = usuarioRepository.findSolicitanteByTramiteId(tramiteId);
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(titulo + ": " + mensaje);
        notificacion.setFecha(new Date());
        notificacion.setDestinatario(destinatario); // Se asegura de que se guarde el destinatario

        

        notificacionRepository.save(notificacion);
    }
}
