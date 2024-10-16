package com.example.colombina.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.colombina.entidad.EntidadSanitariaINVIMA;

public interface EntidadSanitariaINVIMARepository extends JpaRepository<EntidadSanitariaINVIMA, Long>{

    // Buscar por resultado del trámite (true o false)
    List<EntidadSanitariaINVIMA> findByResultadoTramite(boolean resultadoTramite);
    
    // Buscar por fecha de radicación
    List<EntidadSanitariaINVIMA> findByFechaRadicacion(Date fechaRadicacion);
    
    // Buscar por comentarios que contengan una palabra clave
    List<EntidadSanitariaINVIMA> findByComentariosContaining(String keyword);
    
}
