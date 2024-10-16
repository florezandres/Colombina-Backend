package com.example.colombina.repositorio;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.colombina.entidad.Notificacion;
import com.example.colombina.entidad.Tramite;

public interface NotificacionRepository extends JpaRepository <Notificacion, Long>{
    Optional<Notificacion> findByMensaje(String mensaje);

    Optional<Notificacion> findByTramite(Tramite tramite);

    
} 
