package com.example.colombina.repositories;

import com.example.colombina.model.EntidadSanitaria;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntidadSanitariaRepository extends JpaRepository<EntidadSanitaria, Long> {
    @NotNull
    List<EntidadSanitaria> findAll();
}
