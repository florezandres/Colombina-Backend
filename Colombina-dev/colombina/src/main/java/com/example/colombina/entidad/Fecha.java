package com.example.colombina.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Fecha {
    //Atributos
    @Id
    @GeneratedValue
    private Long fechaId;

    private int dia;
    private int mes;
    private int año;

    //Relaciones
    @OneToOne(mappedBy = "fecha")
    private Tramite tramite;

    //Constructores
    public Fecha(Long fechaId, int dia, int mes, int año, Tramite tramite) {
        this.fechaId = fechaId;
        this.dia = dia;
        this.mes = mes;
        this.año = año;
    }

    public Fecha() {}

    public Fecha(int dia, int mes, int año) {
        this.dia = dia;
        this.mes = mes;
        this.año = año;
    }

    //Getters y setters
    public Long getId() {
        return fechaId;
    }

    public void setId(Long fechaId) {
        this.fechaId = fechaId;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    
}
