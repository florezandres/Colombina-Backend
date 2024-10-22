package com.example.colombina.DTOs;

import java.time.LocalDateTime;

public class VersionDocumentoDTO {

    private Long id;
    private String nombreArchivo;
    private String urlArchivo;
    private LocalDateTime fechaCreacion;
    private String cambiosDestacados;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCambiosDestacados() {
        return cambiosDestacados;
    }

    public void setCambiosDestacados(String cambiosDestacados) {
        this.cambiosDestacados = cambiosDestacados;
    }
}
