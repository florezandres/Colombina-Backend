package com.example.colombina.controlador;

import com.example.colombina.entidad.AsuntoRegulatorio;
import com.example.colombina.servicio.AsuntoRegulatorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AsuntoRegulatorioControllerTest {

    @Mock
    private AsuntoRegulatorioService asuntoRegulatorioService;

    @InjectMocks
    private AsuntoRegulatorioController asuntoRegulatorioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearAsuntoRegulatorio() {
        AsuntoRegulatorio asunto = new AsuntoRegulatorio("Responsable1", true, new Date(System.currentTimeMillis()));
        when(asuntoRegulatorioService.crearAsuntoRegulatorio(asunto)).thenReturn(asunto);

        ResponseEntity<AsuntoRegulatorio> response = asuntoRegulatorioController.crearAsuntoRegulatorio(asunto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(asunto, response.getBody());
    }

    @Test
    void testObtenerTodosLosAsuntosRegulatorios() {
        List<AsuntoRegulatorio> asuntos = Arrays.asList(
                new AsuntoRegulatorio("Responsable1", true, new Date(System.currentTimeMillis())),
                new AsuntoRegulatorio("Responsable2", false, new Date(System.currentTimeMillis()))
        );
        when(asuntoRegulatorioService.obtenerTodosLosAsuntosRegulatorios()).thenReturn(asuntos);

        ResponseEntity<List<AsuntoRegulatorio>> response = asuntoRegulatorioController.obtenerTodosLosAsuntosRegulatorios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(asuntos, response.getBody());
    }

    @Test
    void testObtenerAsuntoRegulatorioPorId() {
        Long id = 1L;
        AsuntoRegulatorio asunto = new AsuntoRegulatorio("Responsable1", true, new Date(System.currentTimeMillis()));
        when(asuntoRegulatorioService.obtenerAsuntoRegulatorioPorId(id)).thenReturn(Optional.of(asunto));

        ResponseEntity<AsuntoRegulatorio> response = asuntoRegulatorioController.obtenerAsuntoRegulatorioPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(asunto, response.getBody());
    }

    @Test
    void testObtenerAsuntoRegulatorioPorId_NotFound() {
        Long id = 1L;
        when(asuntoRegulatorioService.obtenerAsuntoRegulatorioPorId(id)).thenReturn(Optional.empty());

        ResponseEntity<AsuntoRegulatorio> response = asuntoRegulatorioController.obtenerAsuntoRegulatorioPorId(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testActualizarAsuntoRegulatorio() {
        Long id = 1L;
        AsuntoRegulatorio asunto = new AsuntoRegulatorio("Responsable1", true, new Date(System.currentTimeMillis()));
        when(asuntoRegulatorioService.actualizarAsuntoRegulatorio(id, asunto)).thenReturn(asunto);

        ResponseEntity<AsuntoRegulatorio> response = asuntoRegulatorioController.actualizarAsuntoRegulatorio(id, asunto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(asunto, response.getBody());
    }

    @Test
    void testEliminarAsuntoRegulatorio() {
        Long id = 1L;

        ResponseEntity<Void> response = asuntoRegulatorioController.eliminarAsuntoRegulatorio(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(asuntoRegulatorioService, times(1)).eliminarAsuntoRegulatorio(id);
    }

    @Test
    void testBuscarPorResponsable() {
        String responsable = "Responsable1";
        List<AsuntoRegulatorio> asuntos = Arrays.asList(
                new AsuntoRegulatorio("Responsable1", true, new Date(System.currentTimeMillis()))
        );
        when(asuntoRegulatorioService.buscarPorResponsable(responsable)).thenReturn(asuntos);

        ResponseEntity<List<AsuntoRegulatorio>> response = asuntoRegulatorioController.buscarPorResponsable(responsable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(asuntos, response.getBody());
    }

    @Test
    void testBuscarPorEstadoAsunto() {
        boolean estadoAsunto = true;
        List<AsuntoRegulatorio> asuntos = Arrays.asList(
                new AsuntoRegulatorio("Responsable1", true, new Date(System.currentTimeMillis()))
        );
        when(asuntoRegulatorioService.buscarPorEstadoAsunto(estadoAsunto)).thenReturn(asuntos);

        ResponseEntity<List<AsuntoRegulatorio>> response = asuntoRegulatorioController.buscarPorEstadoAsunto(estadoAsunto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(asuntos, response.getBody());
    }

    @Test
    void testBuscarPorFechaAsunto() {
        Date fechaAsunto = new Date(System.currentTimeMillis());
        List<AsuntoRegulatorio> asuntos = Arrays.asList(
                new AsuntoRegulatorio("Responsable1", true, fechaAsunto)
        );
        when(asuntoRegulatorioService.buscarPorFechaAsunto(fechaAsunto)).thenReturn(asuntos);

        ResponseEntity<List<AsuntoRegulatorio>> response = asuntoRegulatorioController.buscarPorFechaAsunto(fechaAsunto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(asuntos, response.getBody());
    }
}
