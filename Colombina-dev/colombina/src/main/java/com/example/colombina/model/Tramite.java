package com.example.colombina.model;

import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL)
    private List<Documento> documentos;

    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL)
    private List<Pago> pagos;

    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL)
    private List<Seguimiento> seguimientos;

    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL)
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
}
