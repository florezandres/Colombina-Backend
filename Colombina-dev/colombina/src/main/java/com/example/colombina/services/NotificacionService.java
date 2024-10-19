package com.example.colombina.services;

import org.springframework.stereotype.Service;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

@Service
public class NotificacionService {
    private final Resend resendClient;

    public NotificacionService(Resend resendClient) {
        this.resendClient = resendClient;
    }

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
}
