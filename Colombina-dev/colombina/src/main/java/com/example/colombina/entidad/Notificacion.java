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
    private Long idNotificacion;

    private String mensaje;
    private boolean estado;
    private String tipoNotificacion;
    

    // Relaciones
    @ManyToOne
    private Tramite tramite;

    // Constructores

    public Notificacion() {}

    public Notificacion(String mensaje, boolean estado, String tipoNotificacion, Tramite tramite) {
        this.mensaje = mensaje;
        this.estado = estado;
        this.tipoNotificacion = tipoNotificacion;
        this.tramite = tramite;
    }

    public Notificacion(Long idNotificacion, String mensaje, boolean estado, String tipoNotificacion, Tramite tramite) {
        this.idNotificacion = idNotificacion;
        this.mensaje = mensaje;
        this.estado = estado;
        this.tipoNotificacion = tipoNotificacion;
        this.tramite = tramite;
    }

    
    // Getters y setters


    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }
    
}