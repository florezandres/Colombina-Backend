package com.example.colombina.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTramite estado;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaRadicacion;

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
