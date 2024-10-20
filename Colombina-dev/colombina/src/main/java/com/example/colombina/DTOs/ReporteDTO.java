package com.example.colombina.DTOs;

public class ReporteDTO {
    private String numeroRadicado;
    private String estado;
    private String tiempoDeProcesamiento;

    // Getters
    public String getNumeroRadicado() {
        return numeroRadicado;
    }

    public String getEstado() {
        return estado;
    }

    public String getTiempoDeProcesamiento() {
        return tiempoDeProcesamiento;
    }

    // Setters
    public void setNumeroRadicado(String numeroRadicado) {
        this.numeroRadicado = numeroRadicado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTiempoDeProcesamiento(String tiempoDeProcesamiento) {
        this.tiempoDeProcesamiento = tiempoDeProcesamiento;
    }
}