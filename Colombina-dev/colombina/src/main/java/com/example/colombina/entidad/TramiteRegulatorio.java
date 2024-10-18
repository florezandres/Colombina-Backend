package com.example.colombina.entidad;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TramiteRegulatorio {
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
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante;

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
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud;

    // Enum definido dentro de TramiteRegulatorio (opcional)
    public enum EstadoTramite {
        EN_REVISION,
        APROBADO,
        RECHAZADO
    }
}
