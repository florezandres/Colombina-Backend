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

import com.example.colombina.entidad.ArchivoControlDeTramites;
import com.example.colombina.repositorio.ArchivoControlDeTramitesRepository;

class ArchivoControlDeTramitesServiceTest {

    @Mock
    private ArchivoControlDeTramitesRepository archivoRepository;

    @InjectMocks
    private ArchivoControlDeTramitesService archivoService;

    private ArchivoControlDeTramites archivoControlDeTramites;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        archivoControlDeTramites = new ArchivoControlDeTramites(Date.valueOf("2024-10-10"), true, "Primera Modificación");
    }

    @Test
    void crearArchivo_DeberiaGuardarArchivo() {
        when(archivoRepository.save(any(ArchivoControlDeTramites.class))).thenReturn(archivoControlDeTramites);

        ArchivoControlDeTramites resultado = archivoService.crearArchivo(archivoControlDeTramites);

        assertNotNull(resultado);
        assertEquals("Primera Modificación", resultado.getHistorialDeModificaciones());
        verify(archivoRepository, times(1)).save(archivoControlDeTramites);
    }

    @Test
    void obtenerTodosLosArchivos_DeberiaRetornarListaArchivos() {
        ArchivoControlDeTramites archivo1 = new ArchivoControlDeTramites(Date.valueOf("2024-10-10"), true, "Primera Modificación");
        ArchivoControlDeTramites archivo2 = new ArchivoControlDeTramites(Date.valueOf("2024-09-10"), false, "Segunda Modificación");
        List<ArchivoControlDeTramites> listaArchivos = Arrays.asList(archivo1, archivo2);
        when(archivoRepository.findAll()).thenReturn(listaArchivos);

        List<ArchivoControlDeTramites> resultado = archivoService.obtenerTodosLosArchivos();

        assertEquals(2, resultado.size());
        verify(archivoRepository, times(1)).findAll();
    }

    @Test
    void obtenerArchivoPorId_DeberiaRetornarArchivoSiExiste() {
        when(archivoRepository.findById(1L)).thenReturn(Optional.of(archivoControlDeTramites));

        Optional<ArchivoControlDeTramites> resultado = archivoService.obtenerArchivoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Primera Modificación", resultado.get().getHistorialDeModificaciones());
        verify(archivoRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerArchivoPorId_DeberiaRetornarVacioSiNoExiste() {
        when(archivoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ArchivoControlDeTramites> resultado = archivoService.obtenerArchivoPorId(1L);

        assertFalse(resultado.isPresent());
        verify(archivoRepository, times(1)).findById(1L);
    }

    @Test
    void actualizarArchivo_DeberiaActualizarArchivo() {
        ArchivoControlDeTramites archivoActualizado = new ArchivoControlDeTramites(Date.valueOf("2024-11-10"), false, "Modificación actualizada");
        when(archivoRepository.save(any(ArchivoControlDeTramites.class))).thenReturn(archivoActualizado);

        ArchivoControlDeTramites resultado = archivoService.actualizarArchivo(1L, archivoActualizado);

        assertNotNull(resultado);
        assertEquals("Modificación actualizada", resultado.getHistorialDeModificaciones());
        assertEquals(Date.valueOf("2024-11-10"), resultado.getFechaCreacion());
        verify(archivoRepository, times(1)).save(archivoActualizado);
    }

    @Test
    void eliminarArchivo_DeberiaEliminarArchivo() {
        doNothing().when(archivoRepository).deleteById(1L);

        archivoService.eliminarArchivo(1L);

        verify(archivoRepository, times(1)).deleteById(1L);
    }

    @Test
    void buscarPorEstadoTramite_DeberiaRetornarArchivosPorEstado() {
        ArchivoControlDeTramites archivo1 = new ArchivoControlDeTramites(Date.valueOf("2024-10-10"), true, "Modificación 1");
        List<ArchivoControlDeTramites> listaArchivos = Arrays.asList(archivo1);
        when(archivoRepository.findByEstadoTramite(true)).thenReturn(listaArchivos);

        List<ArchivoControlDeTramites> resultado = archivoService.buscarPorEstadoTramite(true);

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isEstadoTramite());
        verify(archivoRepository, times(1)).findByEstadoTramite(true);
    }

    @Test
    void buscarPorFechaCreacion_DeberiaRetornarArchivosPorFecha() {
        ArchivoControlDeTramites archivo1 = new ArchivoControlDeTramites(Date.valueOf("2024-10-10"), true, "Modificación 1");
        List<ArchivoControlDeTramites> listaArchivos = Arrays.asList(archivo1);
        when(archivoRepository.findByFechaCreacion(Date.valueOf("2024-10-10"))).thenReturn(listaArchivos);

        List<ArchivoControlDeTramites> resultado = archivoService.buscarPorFechaCreacion(Date.valueOf("2024-10-10"));

        assertEquals(1, resultado.size());
        assertEquals(Date.valueOf("2024-10-10"), resultado.get(0).getFechaCreacion());
        verify(archivoRepository, times(1)).findByFechaCreacion(Date.valueOf("2024-10-10"));
    }

    @Test
    void buscarPorHistorialDeModificaciones_DeberiaRetornarArchivosPorHistorialDeModificaciones() {
        ArchivoControlDeTramites archivo1 = new ArchivoControlDeTramites(Date.valueOf("2024-10-10"), true, "Primera Modificación");
        List<ArchivoControlDeTramites> listaArchivos = Arrays.asList(archivo1);
        when(archivoRepository.findByHistorialDeModificacionesContaining("Primera")).thenReturn(listaArchivos);

        List<ArchivoControlDeTramites> resultado = archivoService.buscarPorHistorialDeModificaciones("Primera");

        assertEquals(1, resultado.size());
        assertEquals("Primera Modificación", resultado.get(0).getHistorialDeModificaciones());
        verify(archivoRepository, times(1)).findByHistorialDeModificacionesContaining("Primera");
    }
}
