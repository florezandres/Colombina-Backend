package com.example.colombina.model;

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
    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Documento> documentos;

    @JsonIgnore
    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pago> pagos;

    @JsonIgnore
    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seguimiento> seguimientos;

    @JsonIgnore
    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistorialCambio> historialCambios;

    @Column(nullable = false)
    private double progreso;

    @Column()
    private double llave;

    @Column()
    private String pt;

    @Column()
    private String unidadNegocio;

    @Column()
    private Integer numProyectoSap;

    @Column()
    private String proyecto;

    @Column()
    private String tipoModificacion;

    @Column()
    private String descripcionTramite;

    @Column()
    private String claseTramite;

    @OneToOne
    @JoinColumn(name = "solicitud_id")
    @JsonIgnore
    private Solicitud solicitud;

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
        this.llave = 0;
        this.documentos = null;
        this.pagos = null;
        this.seguimientos = null;
        this.historialCambios = null;
        this.setProgreso();
    }

    public String getEtapa() {
        return this.tipoTramite == TipoTramite.NACIONAL ? "A" + etapa.intValue() : "B" + etapa.intValue();
    }

    public Double getProgreso() {
        return this.progreso;
    }

    public void setProgreso() {
        if (this.estado == EstadoTramite.APROBADO) {
            this.progreso = 1;
        } else if (this.estado == EstadoTramite.RECHAZADO) {
            this.progreso = 0;
        } else {
            this.progreso = this.tipoTramite == TipoTramite.NACIONAL ? (double) (etapa - 1) / 9 : (double) (etapa - 1) / 8;
        }
    }

    // Enum definido dentro de TramiteRegulatorio (opcional)
    public enum EstadoTramite {
        EN_REVISION, // Asuntos regulatorios revisando
        APROBADO, // Aprobado
        RECHAZADO, // Rechazado
        PENDIENTE, // Pendiente a procesar la solicitud
    }

    public enum TipoTramite {
        NACIONAL,
        INTERNACIONAL
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
