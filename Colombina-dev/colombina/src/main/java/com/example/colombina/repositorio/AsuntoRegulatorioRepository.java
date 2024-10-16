package com.example.colombina.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.colombina.entidad.AsuntoRegulatorio;

@Repository
public interface AsuntoRegulatorioRepository extends JpaRepository <AsuntoRegulatorio, Long>{

     // Buscar asuntos regulatorios por responsable
     List<AsuntoRegulatorio> findByResponsable(String responsable);
    
     // Buscar asuntos regulatorios por estado
     List<AsuntoRegulatorio> findByEstadoAsunto(boolean estadoAsunto);
     
     // Buscar asuntos regulatorios por fecha de creación
     List<AsuntoRegulatorio> findByFechaAsunto(Date fechaAsunto);
    
}
