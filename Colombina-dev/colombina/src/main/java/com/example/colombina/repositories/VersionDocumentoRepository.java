package com.example.colombina.repositories;
import com.example.colombina.model.VersionDocumento;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersionDocumentoRepository extends JpaRepository<VersionDocumento, Long> {
    List<VersionDocumento> findByDocumentoId(Long documentoId);
}
