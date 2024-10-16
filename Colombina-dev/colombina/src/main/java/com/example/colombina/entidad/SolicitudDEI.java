package com.example.colombina.entidad;


import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class SolicitudDEI {
    // Atributos
    @Id
    @GeneratedValue
    private Long idSolicitud;
    private String nombreSolicitante;
    private Date fechaSolicitud;
    private String descripcion;
    private String tipoDeProducto;
    private String documentacionTecnica;
    private String tipoDeRegistro;

    //Relaciones
    @OneToOne
    @JoinColumn(name = "tramiteId")
    private Tramite tramite;

    // Constructores
    public SolicitudDEI() {
    }

    public SolicitudDEI(String nombreSolicitante, Date fechaSolicitud, String descripcion, String tipoDeProducto, String documentacionTecnica, String tipoDeRegistro) {
        this.nombreSolicitante = nombreSolicitante;
        this.fechaSolicitud = fechaSolicitud;
        this.descripcion = descripcion;
        this.tipoDeProducto = tipoDeProducto;
        this.documentacionTecnica = documentacionTecnica;
        this.tipoDeRegistro = tipoDeRegistro;
    }

    public SolicitudDEI(Long idSolicitud, String nombreSolicitante, Date fechaSolicitud, String descripcion, String tipoDeProducto, String documentacionTecnica, String tipoDeRegistro) {
        this.idSolicitud = idSolicitud;
        this.nombreSolicitante = nombreSolicitante;
        this.fechaSolicitud = fechaSolicitud;
        this.descripcion = descripcion;
        this.tipoDeProducto = tipoDeProducto;
        this.documentacionTecnica = documentacionTecnica;
        this.tipoDeRegistro = tipoDeRegistro;
    }

    
    // Getters y setters

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoDeProducto() {
        return tipoDeProducto;
    }

    public void setTipoDeProducto(String tipoDeProducto) {
        this.tipoDeProducto = tipoDeProducto;
    }

    public String getDocumentacionTecnica() {
        return documentacionTecnica;
    }

    public void setDocumentacionTecnica(String documentacionTecnica) {
        this.documentacionTecnica = documentacionTecnica;
    }

    public String getTipoDeRegistro() {
        return tipoDeRegistro;
    }

    public void setTipoDeRegistro(String tipoDeRegistro) {
        this.tipoDeRegistro = tipoDeRegistro;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }
}
