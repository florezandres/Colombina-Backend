package com.example.colombina.controlador;

import com.example.colombina.entidad.ExpedienteRegulatorio;
import com.example.colombina.servicio.ExpedienteRegulatorioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ExpedienteRegulatorioControllerTest {

    @Mock
    private ExpedienteRegulatorioService expedienteRegulatorioService;

    @InjectMocks
    private ExpedienteRegulatorioController expedienteRegulatorioController;

    @Test
    public void testCrearExpedienteRegulatorio() {
        ExpedienteRegulatorio expediente = new ExpedienteRegulatorio(1, Date.valueOf("2023-10-22"), true, "Descripción");
        when(expedienteRegulatorioService.crearExpedienteRegulatorio(expediente)).thenReturn(expediente);

        ResponseEntity<ExpedienteRegulatorio> response = expedienteRegulatorioController.crearExpedienteRegulatorio(expediente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expediente, response.getBody());
        verify(expedienteRegulatorioService, times(1)).crearExpedienteRegulatorio(expediente);
    }

    @Test
    public void testObtenerTodosLosExpedientesRegulatorios() {
        List<ExpedienteRegulatorio> expedientes = Arrays.asList(
            new ExpedienteRegulatorio(1, Date.valueOf("2023-10-22"), true, "Descripción 1"),
            new ExpedienteRegulatorio(2, Date.valueOf("2023-10-23"), false, "Descripción 2")
        );
        when(expedienteRegulatorioService.obtenerTodosLosExpedientesRegulatorios()).thenReturn(expedientes);

        ResponseEntity<List<ExpedienteRegulatorio>> response = expedienteRegulatorioController.obtenerTodosLosExpedientesRegulatorios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expedientes, response.getBody());
        verify(expedienteRegulatorioService, times(1)).obtenerTodosLosExpedientesRegulatorios();
    }

    @Test
    public void testObtenerExpedienteRegulatorioPorId_Encontrado() {
        Long id = 1L;
        ExpedienteRegulatorio expediente = new ExpedienteRegulatorio(1, Date.valueOf("2023-10-22"), true, "Descripción");
        when(expedienteRegulatorioService.obtenerExpedienteRegulatorioPorId(id)).thenReturn(Optional.of(expediente));

        ResponseEntity<ExpedienteRegulatorio> response = expedienteRegulatorioController.obtenerExpedienteRegulatorioPorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expediente, response.getBody());
        verify(expedienteRegulatorioService, times(1)).obtenerExpedienteRegulatorioPorId(id);
    }

    @Test
    public void testObtenerExpedienteRegulatorioPorId_NoEncontrado() {
        Long id = 1L;
        when(expedienteRegulatorioService.obtenerExpedienteRegulatorioPorId(id)).thenReturn(Optional.empty());

        ResponseEntity<ExpedienteRegulatorio> response = expedienteRegulatorioController.obtenerExpedienteRegulatorioPorId(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(expedienteRegulatorioService, times(1)).obtenerExpedienteRegulatorioPorId(id);
    }

    @Test
    public void testActualizarExpedienteRegulatorio() {
        Long id = 1L;
        ExpedienteRegulatorio expediente = new ExpedienteRegulatorio(1, Date.valueOf("2023-10-22"), true, "Descripción actualizada");
        when(expedienteRegulatorioService.actualizarExpedienteRegulatorio(id, expediente)).thenReturn(expediente);

        ResponseEntity<ExpedienteRegulatorio> response = expedienteRegulatorioController.actualizarExpedienteRegulatorio(id, expediente);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expediente, response.getBody());
        verify(expedienteRegulatorioService, times(1)).actualizarExpedienteRegulatorio(id, expediente);
    }

    @Test
    public void testEliminarExpedienteRegulatorio() {
        Long id = 1L;

        ResponseEntity<Void> response = expedienteRegulatorioController.eliminarExpedienteRegulatorio(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(expedienteRegulatorioService, times(1)).eliminarExpedienteRegulatorio(id);
    }
}
