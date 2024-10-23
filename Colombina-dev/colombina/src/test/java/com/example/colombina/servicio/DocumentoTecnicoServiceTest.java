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

import com.example.colombina.entidad.DocumentoTecnico;
import com.example.colombina.repositorio.DocumentoTecnicoRepository;

class DocumentoTecnicoServiceTest {

    @Mock
    private DocumentoTecnicoRepository documentoTecnicoRepository;

    @InjectMocks
    private DocumentoTecnicoService documentoTecnicoService;

    private DocumentoTecnico documentoTecnico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        documentoTecnico = new DocumentoTecnico("TipoA", Date.valueOf("2024-10-10"), "Descripción", true);
    }

    @Test
    void crearDocumentoTecnico_DeberiaGuardarDocumentoTecnico() {
        when(documentoTecnicoRepository.save(any(DocumentoTecnico.class))).thenReturn(documentoTecnico);

        DocumentoTecnico resultado = documentoTecnicoService.crearDocumentoTecnico(documentoTecnico);

        assertNotNull(resultado);
        assertEquals("TipoA", resultado.getTipoDocumento());
        assertEquals("Descripción", resultado.getDescripcion());
        verify(documentoTecnicoRepository, times(1)).save(documentoTecnico);
    }

    @Test
    void obtenerTodosLosDocumentosTecnicos_DeberiaRetornarListaDocumentosTecnicos() {
        DocumentoTecnico documento1 = new DocumentoTecnico("TipoA", Date.valueOf("2024-10-10"), "Descripción 1", true);
        DocumentoTecnico documento2 = new DocumentoTecnico("TipoB", Date.valueOf("2024-09-10"), "Descripción 2", false);
        List<DocumentoTecnico> listaDocumentos = Arrays.asList(documento1, documento2);
        when(documentoTecnicoRepository.findAll()).thenReturn(listaDocumentos);

        List<DocumentoTecnico> resultado = documentoTecnicoService.obtenerTodosLosDocumentosTecnicos();

        assertEquals(2, resultado.size());
        verify(documentoTecnicoRepository, times(1)).findAll();
    }

    @Test
    void obtenerDocumentoTecnicoPorId_DeberiaRetornarDocumentoTecnicoSiExiste() {
        when(documentoTecnicoRepository.findById(1L)).thenReturn(Optional.of(documentoTecnico));

        Optional<DocumentoTecnico> resultado = documentoTecnicoService.obtenerDocumentoTecnicoPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("TipoA", resultado.get().getTipoDocumento());
        verify(documentoTecnicoRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerDocumentoTecnicoPorId_DeberiaRetornarVacioSiNoExiste() {
        when(documentoTecnicoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<DocumentoTecnico> resultado = documentoTecnicoService.obtenerDocumentoTecnicoPorId(1L);

        assertFalse(resultado.isPresent());
        verify(documentoTecnicoRepository, times(1)).findById(1L);
    }

    @Test
    void actualizarDocumentoTecnico_DeberiaActualizarDocumentoTecnico() {
        DocumentoTecnico documentoActualizado = new DocumentoTecnico("TipoB", Date.valueOf("2024-11-10"), "Descripción actualizada", false);
        when(documentoTecnicoRepository.save(any(DocumentoTecnico.class))).thenReturn(documentoActualizado);

        DocumentoTecnico resultado = documentoTecnicoService.actualizarDocumentoTecnico(1L, documentoActualizado);

        assertNotNull(resultado);
        assertEquals("TipoB", resultado.getTipoDocumento());
        assertEquals("Descripción actualizada", resultado.getDescripcion());
        verify(documentoTecnicoRepository, times(1)).save(documentoActualizado);
    }

    @Test
    void eliminarDocumentoTecnico_DeberiaEliminarDocumentoTecnico() {
        doNothing().when(documentoTecnicoRepository).deleteById(1L);

        documentoTecnicoService.eliminarDocumentoTecnico(1L);

        verify(documentoTecnicoRepository, times(1)).deleteById(1L);
    }

    @Test
    void buscarPorTipoDocumento_DeberiaRetornarDocumentosPorTipo() {
        DocumentoTecnico documento1 = new DocumentoTecnico("TipoA", Date.valueOf("2024-10-10"), "Descripción 1", true);
        DocumentoTecnico documento2 = new DocumentoTecnico("TipoA", Date.valueOf("2024-11-10"), "Descripción 2", true);
        List<DocumentoTecnico> listaDocumentos = Arrays.asList(documento1, documento2);
        when(documentoTecnicoRepository.findByTipoDocumento("TipoA")).thenReturn(listaDocumentos);

        List<DocumentoTecnico> resultado = documentoTecnicoService.buscarPorTipoDocumento("TipoA");

        assertEquals(2, resultado.size());
        verify(documentoTecnicoRepository, times(1)).findByTipoDocumento("TipoA");
    }

    @Test
    void buscarPorFechaAdjuncion_DeberiaRetornarDocumentosPorFecha() {
        DocumentoTecnico documento1 = new DocumentoTecnico("TipoA", Date.valueOf("2024-10-10"), "Descripción 1", true);
        List<DocumentoTecnico> listaDocumentos = Arrays.asList(documento1);
        when(documentoTecnicoRepository.findByFechaAdjuncion(Date.valueOf("2024-10-10"))).thenReturn(listaDocumentos);

        List<DocumentoTecnico> resultado = documentoTecnicoService.buscarPorFechaAdjuncion(Date.valueOf("2024-10-10"));

        assertEquals(1, resultado.size());
        assertEquals("TipoA", resultado.get(0).getTipoDocumento());
        verify(documentoTecnicoRepository, times(1)).findByFechaAdjuncion(Date.valueOf("2024-10-10"));
    }

    @Test
    void buscarPorEstadoDocumento_DeberiaRetornarDocumentosPorEstado() {
        DocumentoTecnico documento1 = new DocumentoTecnico("TipoA", Date.valueOf("2024-10-10"), "Descripción 1", true);
        List<DocumentoTecnico> listaDocumentos = Arrays.asList(documento1);
        when(documentoTecnicoRepository.findByEstadoDocumento(true)).thenReturn(listaDocumentos);

        List<DocumentoTecnico> resultado = documentoTecnicoService.buscarPorEstadoDocumento(true);

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isEstadoDocumento());
        verify(documentoTecnicoRepository, times(1)).findByEstadoDocumento(true);
    }
}
