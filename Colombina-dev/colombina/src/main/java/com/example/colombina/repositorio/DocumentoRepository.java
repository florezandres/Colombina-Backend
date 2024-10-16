package com.example.colombina.repositorio;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.colombina.entidad.Documento;
import com.example.colombina.entidad.Tramite;

public interface DocumentoRepository extends JpaRepository<Documento,Long> {
    Optional<Documento> findById(Long id);
    
    List<Documento> findByTramite(Tramite tramite);
    
    List<Documento> findByInformacionContaining(String informacion);
    
} 
