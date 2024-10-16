package com.example.colombina.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.colombina.entidad.ExpedienteRegulatorio;

public interface ExpedienteRegulatorioRepository extends JpaRepository<ExpedienteRegulatorio, Long>{

    // Buscar expedientes por número de expediente
    List<ExpedienteRegulatorio> findByNumeroExpediente(int numeroExpediente);

    // Buscar expedientes por estado
    List<ExpedienteRegulatorio> findByEstadoExpediente(boolean estadoExpediente);

    // Buscar expedientes por fecha de creación
    List<ExpedienteRegulatorio> findByFechaCreacion(Date fechaCreacion);

    // Buscar expedientes por descripción que contenga una palabra clave
    List<ExpedienteRegulatorio> findByDescripcionExpedienteContaining(String keyword);
    
}
