package com.example.colombina.repositories;

import com.example.colombina.model.Notificacion;
import com.example.colombina.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByDestinatario(Usuario destinatario);
    List<Notificacion> findByDestinatarioAndLeida(Usuario destinatario, boolean leida);
}
