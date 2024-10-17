package com.example.colombina.entidad;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    // Atributos
    @Id
    @GeneratedValue
    private Long idTramite;

    private int numeroRegistro;
    private String tipoTramite;
    private String estadoTramite;
    private Date fechaRadicacion;
    private Date fechaEjecucion;
    private String observaciones;
    private Date plazoRespuesta;


    // Relaciones
    //RELACIONES E-R ANTERIOR
    @ManyToOne
    @JoinColumn(name = "usuarioId", nullable = false)
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "fechaId", nullable = false)
    private Fecha fecha;

    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos = new ArrayList<>();

    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notificacion> notificaciones = new ArrayList<>();

    //RELACIONES E-R NUEVO
    @OneToMany(mappedBy = "tramite")
    private List<PagoPSE> PagosPSE = new ArrayList<>(); 

    @OneToOne
    @JoinColumn(name = "idSolicitud")
    private SolicitudDEI solicitudDEI;

    @ManyToOne
    @JoinColumn(name = "idAsuntoRegulatorio")
    private AsuntoRegulatorio asuntoRegulatorio;

    @ManyToOne
    @JoinColumn(name = "idArchivoControlDeTramites")
    private ArchivoControlDeTramites archivoControlDeTramites;
    @OneToOne
    @JoinColumn(name = "idEntidad")
    private EntidadSanitariaINVIMA entidadSanitariaINVIMA;

    @OneToOne
    @JoinColumn(name = "idSeguimiento")
    private SeguimientoTramite seguimientoTramite;

    @OneToMany (mappedBy = "tramite")
    private List<ExpedienteRegulatorio> expedienteRegulatorio = new ArrayList<>();
}
