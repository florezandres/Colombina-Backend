package com.example.colombina.repositories;

import com.example.colombina.model.EntidadSanitaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntidadSanitariaRepository extends JpaRepository<EntidadSanitaria, Long> {
}
