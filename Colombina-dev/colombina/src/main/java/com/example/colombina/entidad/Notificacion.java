package com.example.colombina.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Notificacion {
    // Atributos
    @Id
    @GeneratedValue
    private Long notificacionId;

    private String mensaje;

    // Relaciones
    @ManyToOne
    private Tramite tramite;

    // Constructores
    public Notificacion(Long notificacionId, String mensaje, Tramite tramite) {
        this.notificacionId = notificacionId;
        this.mensaje = mensaje;
        this.tramite = tramite;
    }

    public Notificacion() {
    }

    public Notificacion(String mensaje, Tramite tramite) {
        this.mensaje = mensaje;
        this.tramite = tramite;
    }

    // Getters y Setters
    public Long getNotificacionId() {
        return notificacionId;
    }

    public void setNotificacionId(Long notificacionId) {
        this.notificacionId = notificacionId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }
}
    