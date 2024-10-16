package com.example.colombina.entidad;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class SeguimientoTramite {
    @Id
    @GeneratedValue
    private Long idSeguimiento;
    private Date fechaSeguimiento;
    private String estadoActual;
    private String accionesRealizadas;
    private String observaciones;

    @OneToOne
    @JoinColumn(name = "idTramite")
    private Tramite tramite;

    public SeguimientoTramite() {
    }

    public SeguimientoTramite(Date fechaSeguimiento, String estadoActual, String accionesRealizadas, String observaciones) {
        this.fechaSeguimiento = fechaSeguimiento;
        this.estadoActual = estadoActual;
        this.accionesRealizadas = accionesRealizadas;
        this.observaciones = observaciones;
    }

    public SeguimientoTramite(Long idSeguimiento, Date fechaSeguimiento, String estadoActual, String accionesRealizadas, String observaciones) {
        this.idSeguimiento = idSeguimiento;
        this.fechaSeguimiento = fechaSeguimiento;
        this.estadoActual = estadoActual;
        this.accionesRealizadas = accionesRealizadas;
        this.observaciones = observaciones;
    }

    public Long getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Long idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public Date getFechaSeguimiento() {
        return fechaSeguimiento;
    }

    public void setFechaSeguimiento(Date fechaSeguimiento) {
        this.fechaSeguimiento = fechaSeguimiento;
    }

    public String getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(String estadoActual) {
        this.estadoActual = estadoActual;
    }

    public String getAccionesRealizadas() {
        return accionesRealizadas;
    }

    public void setAccionesRealizadas(String accionesRealizadas) {
        this.accionesRealizadas = accionesRealizadas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    
}
