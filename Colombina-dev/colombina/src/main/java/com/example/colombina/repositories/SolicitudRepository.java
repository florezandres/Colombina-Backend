package com.example.colombina.repositories;

import com.example.colombina.DTOs.SolicitudDTO;
import com.example.colombina.model.Solicitud;
import com.example.colombina.model.Tramite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
}
