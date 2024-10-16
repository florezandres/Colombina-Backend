package com.example.colombina.servicio;

import com.example.colombina.entidad.Notificacion;
import com.example.colombina.entidad.Tramite;
import com.example.colombina.repositorio.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }


    public Notificacion crearNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }


    public List<Notificacion> obtenerTodasLasNotificaciones() {
        return notificacionRepository.findAll();
    }


    public Optional<Notificacion> obtenerNotificacionPorId(Long id) {
        return notificacionRepository.findById(id);
    }


    public Notificacion actualizarNotificacion(Long id, Notificacion detallesNotificacion) {
        Optional<Notificacion> notificacionExistente = notificacionRepository.findById(id);
        if (notificacionExistente.isPresent()) {
            Notificacion notificacionActualizada = notificacionExistente.get();
            notificacionActualizada.setMensaje(detallesNotificacion.getMensaje());
            notificacionActualizada.setTramite(detallesNotificacion.getTramite());
            return notificacionRepository.save(notificacionActualizada);
        } else {
            throw new RuntimeException("Notificación no encontrada con el ID: " + id);
        }
    }


    public void eliminarNotificacion(Long id) {
        Optional<Notificacion> notificacion = notificacionRepository.findById(id);
        if (notificacion.isPresent()) {
            notificacionRepository.delete(notificacion.get());
        } else {
            throw new RuntimeException("Notificación no encontrada con el ID: " + id);
        }
    }


    public Optional<Notificacion> obtenerNotificacionPorMensaje(String mensaje) {
        return notificacionRepository.findByMensaje(mensaje);
    }


    public Optional<Notificacion> obtenerNotificacionPorTramite(Tramite tramite) {
        return notificacionRepository.findByTramite(tramite);
    }
}

