package com.example.colombina.servicio;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.colombina.entidad.Fecha;
import com.example.colombina.entidad.Tramite;
import com.example.colombina.entidad.Usuario;
import com.example.colombina.repositorio.TramiteRepository;

class TramiteServiceTest {

    @Mock
    private TramiteRepository tramiteRepository;

    @InjectMocks
    private TramiteService tramiteService;

    private Usuario usuario;
    private Fecha fecha;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        fecha = new Fecha();
    }

    @Test
    void crearTramite_DeberiaGuardarTramite() {
        Tramite tramite = new Tramite(1, "TipoA", "Estado", Date.valueOf("2024-01-01"), Date.valueOf("2024-02-01"), "Observaciones", Date.valueOf("2024-03-01"), usuario, fecha);
        when(tramiteRepository.save(any(Tramite.class))).thenReturn(tramite);

        Tramite resultado = tramiteService.crearTramite(tramite);

        assertNotNull(resultado);
        verify(tramiteRepository, times(1)).save(tramite);
    }

    @Test
    void obtenerTodosLosTramites_DeberiaRetornarListaTramites() {
        Tramite tramite1 = new Tramite(1, "TipoA", "Estado1", Date.valueOf("2024-01-01"), Date.valueOf("2024-02-01"), "Observaciones", Date.valueOf("2024-03-01"), usuario, fecha);
        Tramite tramite2 = new Tramite(2, "TipoB", "Estado2", Date.valueOf("2024-02-01"), Date.valueOf("2024-03-01"), "Observaciones2", Date.valueOf("2024-04-01"), usuario, fecha);
        List<Tramite> listaTramites = Arrays.asList(tramite1, tramite2);
        when(tramiteRepository.findAll()).thenReturn(listaTramites);

        List<Tramite> resultado = tramiteService.obtenerTodosLosTramites();

        assertEquals(2, resultado.size());
        verify(tramiteRepository, times(1)).findAll();
    }

    @Test
    void obtenerTramitePorId_DeberiaRetornarTramiteSiExiste() {
        Tramite tramite = new Tramite(1, "Tipo1", "Estado1", Date.valueOf("2024-01-01"), Date.valueOf("2024-02-01"), "Observaciones", Date.valueOf("2024-03-01"), usuario, fecha);
        when(tramiteRepository.findById(1L)).thenReturn(Optional.of(tramite));

        Optional<Tramite> resultado = tramiteService.obtenerTramitePorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(tramite, resultado.get());
        verify(tramiteRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerTramitePorId_DeberiaRetornarVacioSiNoExiste() {
        when(tramiteRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Tramite> resultado = tramiteService.obtenerTramitePorId(1L);

        assertFalse(resultado.isPresent());
        verify(tramiteRepository, times(1)).findById(1L);
    }

    @Test
    void actualizarTramite_DeberiaActualizarTramiteSiExiste() {
        Tramite tramiteExistente = new Tramite(1, "Tipo1", "Estado1 ", Date.valueOf("2024-01-01"), Date.valueOf("2024-02-01"), "Observaciones", Date.valueOf("2024-03-01"), usuario, fecha);
        Tramite detallesTramite = new Tramite(1, "Tipo1", "EstadoActualizado", Date.valueOf("2024-10-10"), null, "n/a", null, usuario, fecha);

        when(tramiteRepository.findById(1L)).thenReturn(Optional.of(tramiteExistente));
        when(tramiteRepository.save(any(Tramite.class))).thenReturn(tramiteExistente);

        Tramite resultado = tramiteService.actualizarTramite(1L, detallesTramite);

        assertEquals("EstadoActualizado", tramiteExistente.getEstadoTramite());
        assertEquals(Date.valueOf("2024-10-10"), tramiteExistente.getFechaRadicacion());
        verify(tramiteRepository, times(1)).findById(1L);
        verify(tramiteRepository, times(1)).save(tramiteExistente);
    }

    @Test
    void actualizarTramite_DeberiaLanzarExcepcionSiTramiteNoExiste() {
        Tramite detallesTramite = new Tramite(1, "Tipo1", "EstadoActualizado", Date.valueOf("2024-10-10"), null, "n/a", null, usuario, fecha);
        when(tramiteRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tramiteService.actualizarTramite(1L, detallesTramite);
        });
        assertEquals("Trámite no encontrado con el ID: 1", exception.getMessage());
        verify(tramiteRepository, times(1)).findById(1L);
    }

    @Test
    void eliminarTramite_DeberiaEliminarTramiteSiExiste() {
        Tramite tramite = new Tramite(1, "Tipo1", "Estado1", Date.valueOf("2024-01-01"), Date.valueOf("2024-02-01"), "Observaciones", Date.valueOf("2024-03-01"), usuario, fecha);
        when(tramiteRepository.findById(1L)).thenReturn(Optional.of(tramite));

        tramiteService.eliminarTramite(1L);

        verify(tramiteRepository, times(1)).findById(1L);
        verify(tramiteRepository, times(1)).delete(tramite);
    }

    @Test
    void eliminarTramite_DeberiaLanzarExcepcionSiTramiteNoExiste() {
        when(tramiteRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tramiteService.eliminarTramite(1L);
        });
        assertEquals("Trámite no encontrado con el ID: 1", exception.getMessage());
        verify(tramiteRepository, times(1)).findById(1L);
    }
}
