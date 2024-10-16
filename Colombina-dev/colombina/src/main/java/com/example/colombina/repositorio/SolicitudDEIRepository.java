package com.example.colombina.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.colombina.entidad.SolicitudDEI;

public interface SolicitudDEIRepository extends JpaRepository<SolicitudDEI, Long>{

    // Método para encontrar solicitudes por el nombre del solicitante
    List<SolicitudDEI> findByNombreSolicitante(String nombreSolicitante);

    // Método para encontrar solicitudes por tipo de producto
    List<SolicitudDEI> findByTipoDeProducto(String tipoDeProducto);

    // Método para encontrar solicitudes por fecha de solicitud
    List<SolicitudDEI> findByFechaSolicitud(Date fechaSolicitud);
    
} 
