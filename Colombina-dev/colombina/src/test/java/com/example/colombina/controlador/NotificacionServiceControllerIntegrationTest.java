/*package com.example.colombina.controlador;

import com.example.colombina.controlador.NotificacionController;
import com.example.colombina.entidad.Notificacion;
import com.example.colombina.servicio.NotificacionService;

import org.glassfish.jaxb.runtime.v2.schemagen.xmlschema.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificacionControllerIntegrationTest {

    @InjectMocks
    private NotificacionController notificacionController;

    @Mock
    private NotificacionService notificacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearNotificacion() {
        Notificacion notificacion = new Notificacion("Mensaje de prueba", true, "INFO", null);
        when(notificacionService.crearNotificacion(any(Notificacion.class))).thenReturn(notificacion);

        ResponseEntity<Notificacion> response = notificacionController.crearNotificacion(notificacion);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(notificacion, response.getBody());
        verify(notificacionService).crearNotificacion(notificacion);
    }

    @Test
    void testObtenerTodasLasNotificaciones() {
        Notificacion notificacion = new Notificacion("Mensaje de prueba", true, "INFO", null);
        when(notificacionService.obtenerTodasLasNotificaciones()).thenReturn(Collections.singletonList(notificacion));

        ResponseEntity<List<Notificacion>> response = notificacionController.obtenerTodasLasNotificaciones();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(notificacion, response.getBody().get(0));
        verify(notificacionService).obtenerTodasLasNotificaciones();
    }

    @Test
    void testObtenerNotificacionPorId() {
        Long id = 1L;
        Notificacion notificacion = new Notificacion("Mensaje de prueba", true, "INFO", null);
        when(notificacionService.obtenerNotificacionPorId(id)).thenReturn(Optional.of(notificacion));

        ResponseEntity<Notificacion> response = notificacionController.obtenerNotificacionPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notificacion, response.getBody());
        verify(notificacionService).obtenerNotificacionPorId(id);
    }

    @Test
    void testActualizarNotificacion() {
        Long id = 1L;
        Notificacion notificacionExistente = new Notificacion("Mensaje antiguo", true, "INFO", null);
        Notificacion notificacionNueva = new Notificacion("Mensaje nuevo", false, "ERROR", null);
        
        when(notificacionService.obtenerNotificacionPorId(id)).thenReturn(Optional.of(notificacionExistente));
        when(notificacionService.actualizarNotificacion(eq(id), any(Notificacion.class))).thenReturn(notificacionNueva);

        ResponseEntity<Notificacion> response = notificacionController.actualizarNotificacion(id, notificacionNueva);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notificacionNueva, response.getBody());
        verify(notificacionService).actualizarNotificacion(eq(id), any(Notificacion.class));
    }

    @Test
    void testEliminarNotificacion() {
        Long id = 1L;
        doNothing().when(notificacionService).eliminarNotificacion(id);

        ResponseEntity<Void> response = notificacionController.eliminarNotificacion(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(notificacionService).eliminarNotificacion(id);
    }
}
*/