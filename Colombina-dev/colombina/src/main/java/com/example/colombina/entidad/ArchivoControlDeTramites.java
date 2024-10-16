package com.example.colombina.entidad;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class ArchivoControlDeTramites {
    @Id
    @GeneratedValue
    private Long idArchivoControlDeTramites;
    
    private Date fechaCreacion;
    private boolean estadoTramite;
    private String historialDeModificaciones;

    @OneToMany(mappedBy = "archivoControlDeTramites")
    private List<Tramite> tramites = new ArrayList<>();
    
    public ArchivoControlDeTramites() {
    }

    public ArchivoControlDeTramites(Date fechaCreacion, boolean estadoTramite, String historialDeModificaciones) {
        this.fechaCreacion = fechaCreacion;
        this.estadoTramite = estadoTramite;
        this.historialDeModificaciones = historialDeModificaciones;
    }
    
    public ArchivoControlDeTramites(Long idArchivoControlDeTramites, Date fechaCreacion, boolean estadoTramite, String historialDeModificaciones) {
        this.idArchivoControlDeTramites = idArchivoControlDeTramites;
        this.fechaCreacion = fechaCreacion;
        this.estadoTramite = estadoTramite;
        this.historialDeModificaciones = historialDeModificaciones;
    }

    public Long getIdArchivoControlDeTramites() {
        return idArchivoControlDeTramites;
    }

    public void setIdArchivoControlDeTramites(Long idArchivoControlDeTramites) {
        this.idArchivoControlDeTramites = idArchivoControlDeTramites;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isEstadoTramite() {
        return estadoTramite;
    }

    public void setEstadoTramite(boolean estadoTramite) {
        this.estadoTramite = estadoTramite;
    }

    public String getHistorialDeModificaciones() {
        return historialDeModificaciones;
    }

    public void setHistorialDeModificaciones(String historialDeModificaciones) {
        this.historialDeModificaciones = historialDeModificaciones;
    }

    public List<Tramite> getTramites() {
        return tramites;
    }

    public void setTramites(List<Tramite> tramites) {
        this.tramites = tramites;
    }
}

