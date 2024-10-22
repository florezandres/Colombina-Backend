package com.example.colombina.DTOs;

import java.time.LocalDateTime;

public class RevisionManualDTO {

    private Long id;
    private String comentarios;
    private boolean revisionCompleta;
    private LocalDateTime fechaRevision;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public boolean isRevisionCompleta() {
        return revisionCompleta;
    }

    public void setRevisionCompleta(boolean revisionCompleta) {
        this.revisionCompleta = revisionCompleta;
    }

    public LocalDateTime getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(LocalDateTime fechaRevision) {
        this.fechaRevision = fechaRevision;
    }
}
