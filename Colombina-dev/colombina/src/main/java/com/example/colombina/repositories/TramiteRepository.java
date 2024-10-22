package com.example.colombina.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import org.springframework.stereotype.Repository;

import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.Tramite;

@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Long> {
    Optional<Tramite> findByNumeroRadicado(String numeroRadicado);
    List<Tramite> findByEstado(Tramite.EstadoTramite estado);

    //List<Tramite> findByFechaRadicacionAndEstado(Date fechaInicio, Date fechaFin, String tipoTramite, Tramite.EstadoTramite estado);
}
