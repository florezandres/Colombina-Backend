package com.example.colombina.repositories;

import com.example.colombina.model.RevisionManual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RevisionManualRepository extends JpaRepository<RevisionManual, Long> {
    Optional<RevisionManual> findByTramiteId(Long tramiteId);
}
