package com.example.colombina.entidad;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DocumentoTecnico {
    @Id
    @GeneratedValue
    private Long idDocumentoTecnico;

    private String tipoDocumento;
    private Date fechaAdjuncion;
    private String descripcion;
    private boolean estadoDocumento;

    
    @ManyToOne
    @JoinColumn(name = "idExpediente")
    private ExpedienteRegulatorio expedienteRegulatorio;


    public DocumentoTecnico() {
    }

    public DocumentoTecnico(String tipoDocumento, Date fechaAdjuncion, String descripcion, boolean estadoDocumento) {
        this.tipoDocumento = tipoDocumento;
        this.fechaAdjuncion = fechaAdjuncion;
        this.descripcion = descripcion;
        this.estadoDocumento = estadoDocumento;
    }

    public DocumentoTecnico(Long idDocumentoTecnico, String tipoDocumento, Date fechaAdjuncion, String descripcion, boolean estadoDocumento) {
        this.idDocumentoTecnico = idDocumentoTecnico;
        this.tipoDocumento = tipoDocumento;
        this.fechaAdjuncion = fechaAdjuncion;
        this.descripcion = descripcion;
        this.estadoDocumento = estadoDocumento;
    }

    public Long getIdIdDocumentoTecnico() {
        return idDocumentoTecnico;
    }

    public void setIdDocumento(Long idDocumentoTecnico) {
        this.idDocumentoTecnico = idDocumentoTecnico;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Date getFechaAdjuncion() {
        return fechaAdjuncion;
    }

    public void setFechaAdjuncion(Date fechaAdjuncion) {
        this.fechaAdjuncion = fechaAdjuncion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstadoDocumento() {
        return estadoDocumento;
    }

    public void setEstadoDocumento(boolean estadoDocumento) {
        this.estadoDocumento = estadoDocumento;
    }

    public ExpedienteRegulatorio getExpedienteRegulatorio() {
        return expedienteRegulatorio;
    }

    public void setExpedienteRegulatorio(ExpedienteRegulatorio expedienteRegulatorio) {
        this.expedienteRegulatorio = expedienteRegulatorio;
    }

    
}

