package com.example.colombina.entidad;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class AsuntoRegulatorio {

    @Id
    @GeneratedValue
    private Long idAsuntoRegulatorio;

    private String responsable;
    private boolean estadoAsunto;
    private Date fechaAsunto;

    @OneToMany(mappedBy = "asuntoRegulatorio")
    private List<Tramite> tramites = new ArrayList<>();

    public AsuntoRegulatorio() {
    }

    public AsuntoRegulatorio(String responsable, boolean estadoAsunto, Date fechaAsunto) {
        this.responsable = responsable;
        this.estadoAsunto = estadoAsunto;
        this.fechaAsunto = fechaAsunto;
    }
    
    public AsuntoRegulatorio(Long idAsuntoRegulatorio, String responsable, boolean estadoAsunto, Date fechaAsunto) {
        this.idAsuntoRegulatorio = idAsuntoRegulatorio;
        this.responsable = responsable;
        this.estadoAsunto = estadoAsunto;
        this.fechaAsunto = fechaAsunto;
    }

    public Long getIdAsuntoRegulatorio() {
        return idAsuntoRegulatorio;
    }

    public void setIdAsuntoRegulatorio(Long idAsuntoRegulatorio) {
        this.idAsuntoRegulatorio = idAsuntoRegulatorio;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public boolean isEstadoAsunto() {
        return estadoAsunto;
    }

    public void setEstadoAsunto(boolean estadoAsunto) {
        this.estadoAsunto = estadoAsunto;
    }

    public Date getFechaAsunto() {
        return fechaAsunto;
    }

    public void setFechaAsunto(Date fechaAsunto) {
        this.fechaAsunto = fechaAsunto;
    }

    public List<Tramite> getTramites() {
        return tramites;
    }

    public void setTramites(List<Tramite> tramites) {
        this.tramites = tramites;
    }
}   