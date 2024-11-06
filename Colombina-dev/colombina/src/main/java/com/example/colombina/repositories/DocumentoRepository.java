package com.example.colombina.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.colombina.model.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findByTramiteId(Long tramiteId);
    Optional<Documento> findByTramiteIdAndNombre(Long tramiteId, String nombre);
    Long countByTramiteIdAndAprobadoTrue(Long tramiteId);
    Long countByTramiteId(Long tramiteId);

}
