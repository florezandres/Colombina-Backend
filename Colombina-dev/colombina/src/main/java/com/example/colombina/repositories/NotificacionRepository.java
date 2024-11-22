package com.example.colombina.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.colombina.model.Notificacion;
import com.example.colombina.model.Usuario;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByDestinatario(Usuario destinatario);
    List<Notificacion> findByDestinatarioAndLeida(Usuario destinatario, boolean leida);
    Page<Notificacion> findByDestinatarioOrderByFechaDesc(Usuario destinatario, Pageable pageable);
}
