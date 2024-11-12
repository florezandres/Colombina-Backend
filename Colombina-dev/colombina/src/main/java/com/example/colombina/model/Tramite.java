package com.example.colombina.model;

import java.util.Date;
import java.util.List;

import com.example.colombina.DTOs.InfoAperturaTramiteDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    private Date fechaSolicitud;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaRadicacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTramite tipoTramite;

    @Column(nullable = false)
    private Integer etapa = 1;

    private String nombreEtapa;

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
    private Date fechaTerminacion;

    @Column()
    private Date fechaNotificacion;
    
    @Column()
    private Long idSeguimiento;
    
    @Column()
    private String registroSanitario;
    
    @Column()
    private String expedienteRSA;
    
    @Column()
    private Long numeroRSA;
    
    @Column()
    private Date fechaVencimientoRSA;
    
    @Column()
    private String planta;
    
    @Column()
    private String numeroFactura;
    
    @Column()
    private String observaciones;

    @OneToOne
    @JoinColumn(name = "solicitud_id")
    @JsonIgnore
    private Solicitud solicitud;

    public Tramite(Long id) {
        this.id = id;
    }

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
        this.fechaSolicitud = new Date();
    }

    public String getEtapa() {
        return this.tipoTramite == TipoTramite.NACIONAL ? "A" + etapa.intValue() : "B" + etapa.intValue();
    }

    public Double getProgreso() {
        return this.progreso;
    }

    public void setProgreso() {
        setNombreEtapa();
        if (this.estado == EstadoTramite.APROBADO) {
            this.progreso = 1;
        } else if (this.estado == EstadoTramite.RECHAZADO) {
            this.progreso = 0;
        } else {
            if (this.tipoTramite == TipoTramite.NACIONAL) {
                switch (etapa) {
                    case 1:
                        this.progreso = 0.05;
                        break;
                    case 2:
                        this.progreso = 0.05;
                        break;
                    case 3:
                        this.progreso = 0.15;
                        break;
                    case 4:
                        this.progreso = 0.25;
                        break;
                    case 5:
                        this.progreso = 0.4;
                        break;
                    case 6:
                        this.progreso = 0.5;
                        break;
                    case 7:
                        this.progreso = 0.75;
                        break;
                    case 8:
                        this.progreso = 0.85;
                        break;
                    case 9:
                        this.progreso = 0.95;
                        break;
                
                    default:
                        break;
                }
            } else {
                switch (etapa) {
                    case 1:
                        this.progreso = 0.05;
                        break;
                    case 2:
                        this.progreso = 0.15;
                        break;
                    case 3:
                        this.progreso = 0.15;
                        break;
                    case 4:
                        this.progreso = 0.3;
                        break;
                    case 5:
                        this.progreso = 0.45;
                        break;
                    case 6:
                        this.progreso = 0.6;
                        break;
                    case 7:
                        this.progreso = 0.75;
                        break;
                    case 8:
                        this.progreso = 0.9;
                        break;
                
                    default:
                        break;
                }
            }
        }
    }

    public void setEtapa(Integer etapa) {
        this.etapa = etapa;
        setProgreso();
    }

    public void addInfoAperturaTramite(InfoAperturaTramiteDTO data) {
        this.pt = data.getPt();
        this.unidadNegocio = data.getUnidadNegocio();
        this.numProyectoSap = data.getNumProyectoSap();
        this.proyecto = data.getProyecto();
        this.tipoModificacion = data.getTipoModificacion();
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

    public void setNombreEtapa() {
        switch (this.etapa) {
            case 1:
                this.nombreEtapa = "Solicitud de trámite";
                break;
            case 2:
                this.nombreEtapa = "Apertura del trámite";
                break;
            case 3:
                this.nombreEtapa = "Diligenciar información de control";
                break;
            case 4:
                this.nombreEtapa = "Revisión de documentación";
                break;
            case 5:
                this.nombreEtapa = "Consolidación/Radicación del trámite";
                break;
            case 6:
                this.nombreEtapa = "Seguimiento del trámite";
                break;
            case 7:
                this.nombreEtapa = "Aprobación del trámite (Entidad Sanitaria)";
                break;
            case 8:
                this.nombreEtapa = "Aprobación del trámite (Solicitante)";
                break;
            case 9:
                this.nombreEtapa = "Control posterior";
                break;
        }
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
