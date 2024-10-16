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

@Entity
public class Tramite {
    // Atributos
    @Id
    @GeneratedValue
    private Long idTramite;

    private int numeroRegistro;
    private String tipoTramite;
    private boolean estadoTramite;
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

    // Constructores

    public Tramite() {
    }

    public Tramite(int numeroRegistro, String tipoTramite, boolean estadoTramite, Date fechaRadicacion, Date fechaEjecucion, String observaciones, Date plazoRespuesta, Usuario usuario, Fecha fecha) {
        this.numeroRegistro = numeroRegistro;
        this.tipoTramite = tipoTramite;
        this.estadoTramite = estadoTramite;
        this.fechaRadicacion = fechaRadicacion;
        this.fechaEjecucion = fechaEjecucion;
        this.observaciones = observaciones;
        this.plazoRespuesta = plazoRespuesta;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public Tramite(Long idTramite, int numeroRegistro, String tipoTramite, boolean estadoTramite, Date fechaRadicacion,
            Date fechaEjecucion, String observaciones, Date plazoRespuesta, Usuario usuario, Fecha fecha,
            List<Documento> documentos, List<Notificacion> notificaciones) {
        this.idTramite = idTramite;
        this.numeroRegistro = numeroRegistro;
        this.tipoTramite = tipoTramite;
        this.estadoTramite = estadoTramite;
        this.fechaRadicacion = fechaRadicacion;
        this.fechaEjecucion = fechaEjecucion;
        this.observaciones = observaciones;
        this.plazoRespuesta = plazoRespuesta;
        this.usuario = usuario;
        this.fecha = fecha;
        this.documentos = documentos;
        this.notificaciones = notificaciones;
    }

   // Getters y setters

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public int getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(int numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public String getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public boolean isEstadoTramite() {
        return estadoTramite;
    }

    public void setEstadoTramite(boolean estadoTramite) {
        this.estadoTramite = estadoTramite;
    }

    public Date getFechaRadicacion() {
        return fechaRadicacion;
    }

    public void setFechaRadicacion(Date fechaRadicacion) {
        this.fechaRadicacion = fechaRadicacion;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getPlazoRespuesta() {
        return plazoRespuesta;
    }

    public void setPlazoRespuesta(Date plazoRespuesta) {
        this.plazoRespuesta = plazoRespuesta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Fecha getFecha() {
        return fecha;
    }

    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public List<PagoPSE> getPagosPSE() {
        return PagosPSE;
    }

    public void setPagosPSE(List<PagoPSE> pagosPSE) {
        PagosPSE = pagosPSE;
    }

    public SolicitudDEI getSolicitudDEI() {
        return solicitudDEI;
    }

    public void setSolicitudDEI(SolicitudDEI solicitudDEI) {
        this.solicitudDEI = solicitudDEI;
    }

    public AsuntoRegulatorio getAsuntoRegulatorio() {
        return asuntoRegulatorio;
    }

    public void setAsuntosRegulatorios(AsuntoRegulatorio asuntoRegulatorio) {
        this.asuntoRegulatorio = asuntoRegulatorio;
    }

    public ArchivoControlDeTramites getArchivosControlDeTramites() {
        return archivoControlDeTramites;
    }

    public void setArchivosControlDeTramites(ArchivoControlDeTramites archivosControlDeTramites) {
        this.archivoControlDeTramites = archivosControlDeTramites;
    }

    public EntidadSanitariaINVIMA getEntidadSanitariaINVIMA() {
        return entidadSanitariaINVIMA;
    }

    public void setEntidadSanitariaINVIMA(EntidadSanitariaINVIMA entidadSanitariaINVIMA) {
        this.entidadSanitariaINVIMA = entidadSanitariaINVIMA;
    }

    public SeguimientoTramite getSeguimientoTramite() {
        return seguimientoTramite;
    }

    public void setSeguimientoTramite(SeguimientoTramite seguimientoTramite) {
        this.seguimientoTramite = seguimientoTramite;
    }

    public List<ExpedienteRegulatorio> getExpedienteRegulatorio() {
        return expedienteRegulatorio;
    }

    public void setExpedienteRegulatorio(List<ExpedienteRegulatorio> expedienteRegulatorio) {
        this.expedienteRegulatorio = expedienteRegulatorio;
    }

    
}
