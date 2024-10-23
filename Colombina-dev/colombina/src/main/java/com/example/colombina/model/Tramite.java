package com.example.colombina.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @Column(nullable = false)
    private String tipoTramite;

    @ManyToOne
    @JoinColumn(name = "entidad_sanitaria_id", nullable = false)
    private EntidadSanitaria entidadSanitaria;

    @JsonIgnore
    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Documento> documentos;

    @JsonIgnore
    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Pago> pagos;

    @JsonIgnore
    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Seguimiento> seguimientos;

    @JsonIgnore
    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<HistorialCambio> historialCambios;

    @OneToOne
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;

    @Column()
    private double progreso;

    @Column()
    private double llave;

    public Tramite(Long tramiteId, String comentarios, Object o, Object o1, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7) {
    }


    // Enum definido dentro de TramiteRegulatorio (opcional)
    public enum EstadoTramite {
        EN_REVISION, // Asuntos regulatorios revisando
        APROBADO, // Aprobado
        RECHAZADO, // Rechazado
        PENDIENTE, // Pendiente a procesar la solicitud
    }

    public void setNumeroRadicado() {
        this.numeroRadicado = "AR-"+id;
    }

    /*public Tramite(Long id, String numeroRadicado, String nombreProducto, String descripcionProducto, String tipoProducto, EstadoTramite estado, Date fechaRadicacion, String tipoTramite) {
        this.id = id;
        this.numeroRadicado = numeroRadicado;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.tipoProducto = tipoProducto;
        this.estado = estado;
        this.fechaRadicacion = fechaRadicacion;
        this.tipoTramite = tipoTramite;
    }*/
}
