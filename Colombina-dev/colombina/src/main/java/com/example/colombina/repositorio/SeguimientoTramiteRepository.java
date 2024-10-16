package com.example.colombina.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.colombina.entidad.SeguimientoTramite;

public interface SeguimientoTramiteRepository extends JpaRepository<SeguimientoTramite, Long>{

    // Método para buscar seguimientos por el estado actual
    List<SeguimientoTramite> findByEstadoActual(String estadoActual);

    // Método para buscar seguimientos por fecha
    List<SeguimientoTramite> findByFechaSeguimiento(Date fechaSeguimiento);
    
    // Método para buscar por un rango de fechas
    List<SeguimientoTramite> findByFechaSeguimientoBetween(Date fechaInicio, Date fechaFin);
    

    
}
