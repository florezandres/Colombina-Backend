package com.example.colombina.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.colombina.entidad.DocumentoTecnico;

public interface DocumentoTecnicoRepository extends JpaRepository<DocumentoTecnico, Long>{

    // Buscar documentos por tipo de documento
    List<DocumentoTecnico> findByTipoDocumento(String tipoDocumento);
    
    // Buscar documentos por fecha de adjunción
    List<DocumentoTecnico> findByFechaAdjuncion(Date fechaAdjuncion);
    
    // Buscar documentos por estado (activo o inactivo)
    List<DocumentoTecnico> findByEstadoDocumento(boolean estadoDocumento);
    
}
