package com.example.colombina.entidad;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class EntidadSanitariaINVIMA {
    @Id
    @GeneratedValue
    private Long idEntidad;
    private boolean resultadoTramite;
    private Date fechaRadicacion;
    private String comentarios;

    @OneToOne
    @JoinColumn(name = "idTramite")
    private Tramite tramite;

    public EntidadSanitariaINVIMA() {
    }

    public EntidadSanitariaINVIMA(boolean resultadoTramite, Date fechaRadicacion, String comentarios) {
        this.resultadoTramite = resultadoTramite;
        this.fechaRadicacion = fechaRadicacion;
        this.comentarios = comentarios;
    }

    public EntidadSanitariaINVIMA(Long idEntidad, boolean resultadoTramite, Date fechaRadicacion, String comentarios) {
        this.idEntidad = idEntidad;
        this.resultadoTramite = resultadoTramite;
        this.fechaRadicacion = fechaRadicacion;
        this.comentarios = comentarios;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public boolean isResultadoTramite() {
        return resultadoTramite;
    }

    public void setResultadoTramite(boolean resultadoTramite) {
        this.resultadoTramite = resultadoTramite;
    }

    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }    
}
