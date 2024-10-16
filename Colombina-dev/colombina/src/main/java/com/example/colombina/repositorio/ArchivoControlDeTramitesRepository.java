package com.example.colombina.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.colombina.entidad.ArchivoControlDeTramites;

public interface ArchivoControlDeTramitesRepository extends JpaRepository <ArchivoControlDeTramites, Long>{
    
    // Buscar archivos por estado del trámite (true o false)
    List<ArchivoControlDeTramites> findByEstadoTramite (boolean estadoTramite);
    
    // Buscar archivos por fecha de creación
    List<ArchivoControlDeTramites> findByFechaCreacion (Date fechaCreacion);
    
    // Buscar archivos donde el historial de modificaciones contenga una palabra clave
    List<ArchivoControlDeTramites> findByHistorialDeModificacionesContaining (String keyword);
}
