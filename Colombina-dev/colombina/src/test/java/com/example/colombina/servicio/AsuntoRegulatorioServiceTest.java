package com.example.colombina.servicio;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.colombina.entidad.AsuntoRegulatorio;
import com.example.colombina.repositorio.AsuntoRegulatorioRepository;

class AsuntoRegulatorioServiceTest {

    @Mock
    private AsuntoRegulatorioRepository asuntoRegulatorioRepository;

    @InjectMocks
    private AsuntoRegulatorioService asuntoRegulatorioService;

    private AsuntoRegulatorio asuntoRegulatorio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        asuntoRegulatorio = new AsuntoRegulatorio("Juan Pérez", true, Date.valueOf("2024-10-10"));
    }

    @Test
    void crearAsuntoRegulatorio_DeberiaGuardarAsuntoRegulatorio() {
        when(asuntoRegulatorioRepository.save(any(AsuntoRegulatorio.class))).thenReturn(asuntoRegulatorio);

        AsuntoRegulatorio resultado = asuntoRegulatorioService.crearAsuntoRegulatorio(asuntoRegulatorio);

        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getResponsable());
        assertEquals(true, resultado.isEstadoAsunto());
        verify(asuntoRegulatorioRepository, times(1)).save(asuntoRegulatorio);
    }

    @Test
    void obtenerTodosLosAsuntosRegulatorios_DeberiaRetornarListaAsuntos() {
        AsuntoRegulatorio asunto1 = new AsuntoRegulatorio("Juan Pérez", true, Date.valueOf("2024-10-10"));
        AsuntoRegulatorio asunto2 = new AsuntoRegulatorio("Ana García", false, Date.valueOf("2024-09-10"));
        List<AsuntoRegulatorio> listaAsuntos = Arrays.asList(asunto1, asunto2);
        when(asuntoRegulatorioRepository.findAll()).thenReturn(listaAsuntos);

        List<AsuntoRegulatorio> resultado = asuntoRegulatorioService.obtenerTodosLosAsuntosRegulatorios();

        assertEquals(2, resultado.size());
        verify(asuntoRegulatorioRepository, times(1)).findAll();
    }

    @Test
    void obtenerAsuntoRegulatorioPorId_DeberiaRetornarAsuntoSiExiste() {
        when(asuntoRegulatorioRepository.findById(1L)).thenReturn(Optional.of(asuntoRegulatorio));

        Optional<AsuntoRegulatorio> resultado = asuntoRegulatorioService.obtenerAsuntoRegulatorioPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(asuntoRegulatorio, resultado.get());
        verify(asuntoRegulatorioRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerAsuntoRegulatorioPorId_DeberiaRetornarVacioSiNoExiste() {
        when(asuntoRegulatorioRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<AsuntoRegulatorio> resultado = asuntoRegulatorioService.obtenerAsuntoRegulatorioPorId(1L);

        assertFalse(resultado.isPresent());
        verify(asuntoRegulatorioRepository, times(1)).findById(1L);
    }

    @Test
    void actualizarAsuntoRegulatorio_DeberiaActualizarAsuntoRegulatorio() {
        AsuntoRegulatorio asuntoActualizado = new AsuntoRegulatorio("Ana García", false, Date.valueOf("2024-11-10"));
        when(asuntoRegulatorioRepository.save(any(AsuntoRegulatorio.class))).thenReturn(asuntoActualizado);

        AsuntoRegulatorio resultado = asuntoRegulatorioService.actualizarAsuntoRegulatorio(1L, asuntoActualizado);

        assertNotNull(resultado);
        assertEquals("Ana García", resultado.getResponsable());
        assertEquals(false, resultado.isEstadoAsunto());
        verify(asuntoRegulatorioRepository, times(1)).save(asuntoActualizado);
    }

    @Test
    void eliminarAsuntoRegulatorio_DeberiaEliminarAsuntoRegulatorio() {
        doNothing().when(asuntoRegulatorioRepository).deleteById(1L);

        asuntoRegulatorioService.eliminarAsuntoRegulatorio(1L);

        verify(asuntoRegulatorioRepository, times(1)).deleteById(1L);
    }

    @Test
    void buscarPorResponsable_DeberiaRetornarAsuntosPorResponsable() {
        AsuntoRegulatorio asunto1 = new AsuntoRegulatorio("Juan Pérez", true, Date.valueOf("2024-10-10"));
        List<AsuntoRegulatorio> listaAsuntos = Arrays.asList(asunto1);
        when(asuntoRegulatorioRepository.findByResponsable("Juan Pérez")).thenReturn(listaAsuntos);

        List<AsuntoRegulatorio> resultado = asuntoRegulatorioService.buscarPorResponsable("Juan Pérez");

        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getResponsable());
        verify(asuntoRegulatorioRepository, times(1)).findByResponsable("Juan Pérez");
    }

    @Test
    void buscarPorEstadoAsunto_DeberiaRetornarAsuntosPorEstado() {
        AsuntoRegulatorio asunto1 = new AsuntoRegulatorio("Juan Pérez", true, Date.valueOf("2024-10-10"));
        List<AsuntoRegulatorio> listaAsuntos = Arrays.asList(asunto1);
        when(asuntoRegulatorioRepository.findByEstadoAsunto(true)).thenReturn(listaAsuntos);

        List<AsuntoRegulatorio> resultado = asuntoRegulatorioService.buscarPorEstadoAsunto(true);

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isEstadoAsunto());
        verify(asuntoRegulatorioRepository, times(1)).findByEstadoAsunto(true);
    }

    @Test
    void buscarPorFechaAsunto_DeberiaRetornarAsuntosPorFecha() {
        AsuntoRegulatorio asunto1 = new AsuntoRegulatorio("Juan Pérez", true, Date.valueOf("2024-10-10"));
        List<AsuntoRegulatorio> listaAsuntos = Arrays.asList(asunto1);
        when(asuntoRegulatorioRepository.findByFechaAsunto(Date.valueOf("2024-10-10"))).thenReturn(listaAsuntos);

        List<AsuntoRegulatorio> resultado = asuntoRegulatorioService.buscarPorFechaAsunto(Date.valueOf("2024-10-10"));

        assertEquals(1, resultado.size());
        assertEquals(Date.valueOf("2024-10-10"), resultado.get(0).getFechaAsunto());
        verify(asuntoRegulatorioRepository, times(1)).findByFechaAsunto(Date.valueOf("2024-10-10"));
    }
}
