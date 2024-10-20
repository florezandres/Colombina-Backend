package com.example.colombina.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.Tramite;

@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Long> {
    Optional<Tramite> findByNumeroRadicado(String numeroRadicado);
    List<TramiteDTO> findByEstado(Tramite.EstadoTramite estado);

    List<Tramite> findByFechaRadicacionTipoyEstado(Date fechaInicio, Date fechaFin, String tipoTramite, Tramite.EstadoTramite estado);
}
