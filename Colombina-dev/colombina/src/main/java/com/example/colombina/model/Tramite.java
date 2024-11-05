package com.example.colombina.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tramite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroRadicado;

    @Column(nullable = false)
    private String nombreProducto;

    @Column(nullable = false)
    private String descripcionProducto;

    @Column(nullable = false)
    private String tipoProducto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTramite estado;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaRadicacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTramite tipoTramite;

    @Column(nullable = false)
    private Integer etapa;

    @ManyToOne
    @JoinColumn(name = "entidad_sanitaria_id")
    private EntidadSanitaria entidadSanitaria;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    @Column(nullable = false)
    private double progreso;

    // Constructor simplificado
    public Tramite(String numeroRadicado, String nombreProducto, String descripcionProducto, String tipoProducto,
                   EstadoTramite estado, Date fechaRadicacion, TipoTramite tipoTramite, Integer etapa,
                   EntidadSanitaria entidadSanitaria, Solicitud solicitud) {
        this.numeroRadicado = numeroRadicado;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.tipoProducto = tipoProducto;
        this.estado = estado;
        this.fechaRadicacion = fechaRadicacion;
        this.tipoTramite = tipoTramite;
        this.etapa = etapa;
        this.entidadSanitaria = entidadSanitaria;
        this.solicitud = solicitud;
        this.progreso = calcularProgreso();
    }

    public String getEtapa() {
        return this.tipoTramite == TipoTramite.NACIONAL ? "A" + etapa : "B" + etapa;
    }

    public Double getProgreso() {
        BigDecimal bd = new BigDecimal(this.progreso).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void setProgreso() {
        this.progreso = calcularProgreso();
    }

    private double calcularProgreso() {
        return this.tipoTramite == TipoTramite.NACIONAL ? (double) etapa / 9 : (double) etapa / 8;
    }

    public enum EstadoTramite {
        EN_REVISION, APROBADO, RECHAZADO, PENDIENTE
    }

    public enum TipoTramite {
        NACIONAL, INTERNACIONAL
    }

    public void setNumeroRadicado() {
        this.numeroRadicado = "AR-" + id;
    }

    /*
     * public Tramite(Long id, String numeroRadicado, String nombreProducto, String
     * descripcionProducto, String tipoProducto, EstadoTramite estado, Date
     * fechaRadicacion, String tipoTramite) {
     * this.id = id;
     * this.numeroRadicado = numeroRadicado;
     * this.nombreProducto = nombreProducto;
     * this.descripcionProducto = descripcionProducto;
     * this.tipoProducto = tipoProducto;
     * this.estado = estado;
     * this.fechaRadicacion = fechaRadicacion;
     * this.tipoTramite = tipoTramite;
     * }
     */
}
