package com.example.colombina.repositories;

import com.example.colombina.model.Solicitud;
import com.example.colombina.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    @Query("SELECT s FROM Solicitud s LEFT JOIN FETCH s.tramite WHERE s.solicitante = :solicitante")
    List<Solicitud> findBySolicitanteWithTramite(Usuario solicitante);
}
