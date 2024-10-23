package com.example.colombina.controlador;

import com.example.colombina.entidad.SeguimientoTramite;
import com.example.colombina.entidad.Tramite;
import com.example.colombina.servicio.SeguimientoTramiteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SeguimientoTramiteControllerTest {

    @InjectMocks
    private SeguimientoTramiteController seguimientoTramiteController;

    @Mock
    private SeguimientoTramiteService seguimientoTramiteService;

    private SeguimientoTramite seguimientoTramite;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Tramite tramite = new Tramite(); // Suponiendo que Tramite es otra entidad en tu proyecto
        tramite.setIdTramite(1L);

        seguimientoTramite = new SeguimientoTramite(
            1L,
            new Date(System.currentTimeMillis()),
            "En Proceso",
            Collections.singletonList("Acción realizada"),
            "Observaciones de prueba",
            tramite
        );
    }

    @Test
    void testCrearSeguimientoTramite() {
        when(seguimientoTramiteService.crearSeguimientoTramite(seguimientoTramite)).thenReturn(seguimientoTramite);

        ResponseEntity<SeguimientoTramite> response = seguimientoTramiteController.crearSeguimientoTramite(seguimientoTramite);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(seguimientoTramite, response.getBody());
        verify(seguimientoTramiteService, times(1)).crearSeguimientoTramite(seguimientoTramite);
    }

    @Test
    void testObtenerTodosLosSeguimientos() {
        List<SeguimientoTramite> seguimientos = Arrays.asList(seguimientoTramite);
        when(seguimientoTramiteService.obtenerTodosLosSeguimientos()).thenReturn(seguimientos);

        ResponseEntity<List<SeguimientoTramite>> response = seguimientoTramiteController.obtenerTodosLosSeguimientos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(seguimientos, response.getBody());
        verify(seguimientoTramiteService, times(1)).obtenerTodosLosSeguimientos();
    }

    @Test
    void testObtenerSeguimientoTramitePorId() {
        Long id = 1L;
        when(seguimientoTramiteService.obtenerSeguimientoTramitePorId(id)).thenReturn(Optional.of(seguimientoTramite));

        ResponseEntity<SeguimientoTramite> response = seguimientoTramiteController.obtenerSeguimientoTramitePorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(seguimientoTramite, response.getBody());
        verify(seguimientoTramiteService, times(1)).obtenerSeguimientoTramitePorId(id);
    }

    @Test
    void testObtenerSeguimientoTramitePorIdNotFound() {
        Long id = 1L;
        when(seguimientoTramiteService.obtenerSeguimientoTramitePorId(id)).thenReturn(Optional.empty());

        ResponseEntity<SeguimientoTramite> response = seguimientoTramiteController.obtenerSeguimientoTramitePorId(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(seguimientoTramiteService, times(1)).obtenerSeguimientoTramitePorId(id);
    }

    @Test
    void testActualizarSeguimientoTramite() {
        Long id = 1L;
        when(seguimientoTramiteService.actualizarSeguimientoTramite(id, seguimientoTramite)).thenReturn(seguimientoTramite);

        ResponseEntity<SeguimientoTramite> response = seguimientoTramiteController.actualizarSeguimientoTramite(id, seguimientoTramite);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(seguimientoTramite, response.getBody());
        verify(seguimientoTramiteService, times(1)).actualizarSeguimientoTramite(id, seguimientoTramite);
    }

    @Test
    void testEliminarSeguimientoTramite() {
        Long id = 1L;
        doNothing().when(seguimientoTramiteService).eliminarSeguimientoTramite(id);

        ResponseEntity<Void> response = seguimientoTramiteController.eliminarSeguimientoTramite(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(seguimientoTramiteService, times(1)).eliminarSeguimientoTramite(id);
    }

    @Test
    void testBuscarPorEstadoActual() {
        String estadoActual = "En Proceso";
        List<SeguimientoTramite> seguimientos = Arrays.asList(seguimientoTramite);
        when(seguimientoTramiteService.buscarPorEstadoActual(estadoActual)).thenReturn(seguimientos);

        ResponseEntity<List<SeguimientoTramite>> response = seguimientoTramiteController.buscarPorEstadoActual(estadoActual);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(seguimientos, response.getBody());
        verify(seguimientoTramiteService, times(1)).buscarPorEstadoActual(estadoActual);
    }

    @Test
    void testBuscarPorFechaSeguimiento() {
        Date fechaSeguimiento = new Date(System.currentTimeMillis());
        List<SeguimientoTramite> seguimientos = Arrays.asList(seguimientoTramite);
        when(seguimientoTramiteService.buscarPorFechaSeguimiento(fechaSeguimiento)).thenReturn(seguimientos);

        ResponseEntity<List<SeguimientoTramite>> response = seguimientoTramiteController.buscarPorFechaSeguimiento(fechaSeguimiento);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(seguimientos, response.getBody());
        verify(seguimientoTramiteService, times(1)).buscarPorFechaSeguimiento(fechaSeguimiento);
    }

    @Test
    void testBuscarPorRangoDeFechas() {
        Date fechaInicio = new Date(System.currentTimeMillis() - 86400000); // Un día antes
        Date fechaFin = new Date(System.currentTimeMillis());
        List<SeguimientoTramite> seguimientos = Arrays.asList(seguimientoTramite);
        when(seguimientoTramiteService.buscarPorRangoDeFechas(fechaInicio, fechaFin)).thenReturn(seguimientos);

        ResponseEntity<List<SeguimientoTramite>> response = seguimientoTramiteController.buscarPorRangoDeFechas(fechaInicio, fechaFin);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(seguimientos, response.getBody());
        verify(seguimientoTramiteService, times(1)).buscarPorRangoDeFechas(fechaInicio, fechaFin);
    }
}
