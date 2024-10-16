package com.example.colombina.entidad;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class PagoPSE {
    //Atributos
    @Id
    @GeneratedValue
    private Long idPagoPSE;

    private float monto;
    private Date fechaDePago;
    private String metodoDePago;
    private String referenciaDePago;

    //Relaciones

    @ManyToOne
    @JoinColumn(name = "idTramite")
    private Tramite tramite;

    //Constructores

    public PagoPSE() {
    }

    public PagoPSE(float monto, Date fechaDePago, String metodoDePago, String referenciaDePago) {
        this.monto = monto;
        this.fechaDePago = fechaDePago;
        this.metodoDePago = metodoDePago;
        this.referenciaDePago = referenciaDePago;
    }

    public PagoPSE(Long idPagoPSE, float monto, Date fechaDePago, String metodoDePago, String referenciaDePago) {
        this.idPagoPSE = idPagoPSE;
        this.monto = monto;
        this.fechaDePago = fechaDePago;
        this.metodoDePago = metodoDePago;
        this.referenciaDePago = referenciaDePago;
    }


    //Getters y setters

    public Long getIdPagoPSE() {
        return idPagoPSE;
    }

    public void setIdTramite(Long idPagoPSE) {
        this.idPagoPSE = idPagoPSE;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Date getFechaDePago() {
        return fechaDePago;
    }

    public void setFechaDePago(Date fechaDePago) {
        this.fechaDePago = fechaDePago;
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public String getReferenciaDePago() {
        return referenciaDePago;
    }

    public void setReferenciaDePago(String referenciaDePago) {
        this.referenciaDePago = referenciaDePago;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }
}
