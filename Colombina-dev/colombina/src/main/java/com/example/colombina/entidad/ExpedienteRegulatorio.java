package com.example.colombina.entidad;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ExpedienteRegulatorio {
    @Id
    @GeneratedValue
    private Long idExpediente;
    private int numeroExpediente;
    private Date fechaCreacion;
    private boolean estadoExpediente;
    private String descripcionExpediente;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idTramite")
    private Tramite tramite;

    @OneToMany(mappedBy = "idDocumentoTecnico")
    private List<DocumentoTecnico> documentoTecnico = new ArrayList<>(); 

    public ExpedienteRegulatorio() {
    }

    public ExpedienteRegulatorio(int numeroExpediente, Date fechaCreacion, boolean estadoExpediente, String descripcionExpediente) {
        this.numeroExpediente = numeroExpediente;
        this.fechaCreacion = fechaCreacion;
        this.estadoExpediente = estadoExpediente;
        this.descripcionExpediente = descripcionExpediente;
    }

    public ExpedienteRegulatorio(Long idExpediente, int numeroExpediente, Date fechaCreacion, boolean estadoExpediente, String descripcionExpediente) {
        this.idExpediente = idExpediente;
        this.numeroExpediente = numeroExpediente;
        this.fechaCreacion = fechaCreacion;
        this.estadoExpediente = estadoExpediente;
        this.descripcionExpediente = descripcionExpediente;
    }

    public Long getIdExpediente() {
        return idExpediente;
    }

    public void setIdExpediente(Long idExpediente) {
        this.idExpediente = idExpediente;
    }

    public int getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(int numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isEstadoExpediente() {
        return estadoExpediente;
    }

    public void setEstadoExpediente(boolean estadoExpediente) {
        this.estadoExpediente = estadoExpediente;
    }

    public String getDescripcionExpediente() {
        return descripcionExpediente;
    }

    public void setDescripcionExpediente(String descripcionExpediente) {
        this.descripcionExpediente = descripcionExpediente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public List<DocumentoTecnico> getDocumentoTecnico() {
        return documentoTecnico;
    }

    public void setDocumentoTecnico(List<DocumentoTecnico> documentoTecnico) {
        this.documentoTecnico = documentoTecnico;
    }
    
}
