package com.example.colombina.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.colombina.entidad.Notificacion;
import com.example.colombina.entidad.Tramite;
import com.example.colombina.servicio.NotificacionService;
import com.example.colombina.servicio.TramiteService;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;
    private final TramiteService TramiteService;

    public NotificacionController(NotificacionService notificacionService, TramiteService tramiteService) {
        this.notificacionService = notificacionService;
        this.TramiteService = tramiteService; // Asignamos el servicio de Tramite
    }

    // Crear una nueva notificación
    @PostMapping
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion nuevaNotificacion = notificacionService.crearNotificacion(notificacion);
        return new ResponseEntity<>(nuevaNotificacion, HttpStatus.CREATED);
    }

    // Obtener todas las notificaciones
    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerTodasLasNotificaciones() {
        List<Notificacion> notificaciones = notificacionService.obtenerTodasLasNotificaciones();
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }

    // Obtener notificación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> obtenerNotificacionPorId(@PathVariable Long id) {
        Optional<Notificacion> notificacion = notificacionService.obtenerNotificacionPorId(id);
        return notificacion
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Actualizar notificación
    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> actualizarNotificacion(@PathVariable Long id, @RequestBody Notificacion detallesNotificacion) {
        try {
            Notificacion notificacionActualizada = notificacionService.actualizarNotificacion(id, detallesNotificacion);
            return new ResponseEntity<>(notificacionActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar notificación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNotificacion(@PathVariable Long id) {
        try {
            notificacionService.eliminarNotificacion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obtener notificación por mensaje
    @GetMapping("/mensaje/{mensaje}")
    public ResponseEntity<Notificacion> obtenerNotificacionPorMensaje(@PathVariable String mensaje) {
        Optional<Notificacion> notificacion = notificacionService.obtenerNotificacionPorMensaje(mensaje);
        return notificacion
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener notificación por tramite
     // Obtener notificación por tramite
    @GetMapping("/tramite/{tramiteId}")
        public ResponseEntity<Notificacion> obtenerNotificacionPorTramite(@PathVariable Long tramiteId) {
            Optional<Tramite> tramite = TramiteService.obtenerTramitePorId(tramiteId); // Usamos el servicio de Tramite
            if (tramite.isPresent()) {
                Optional<Notificacion> notificacion = notificacionService.obtenerNotificacionPorTramite(tramite.get());
                return notificacion
                        .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si el trámite no existe
            }
        }
}

