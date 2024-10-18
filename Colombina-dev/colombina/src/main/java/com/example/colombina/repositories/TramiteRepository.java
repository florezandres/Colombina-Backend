package com.example.colombina.repositories;

import com.example.colombina.model.Tramite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Long> {
    Optional<Tramite> findByNumeroRadicado(String numeroRadicado);
}
