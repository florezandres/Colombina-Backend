package com.example.colombina.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Documento {
    // Atributos
    @Id
    @GeneratedValue
    private Long documentoId;

    private String informacion;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "tramiteId", nullable = false)
    private Tramite tramite;

    // Constructores

    public Documento(Long documentoId, String informacion, Tramite tramite) {
        this.documentoId = documentoId;
        this.informacion = informacion;
        this.tramite = tramite;
    }

    public Documento() {}

    public Documento(String informacion, Tramite tramite) {
        this.informacion = informacion;
        this.tramite = tramite;
    }

    // Getters y setters
    public Long getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Long documentoId) {
        this.documentoId = documentoId;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    
}
