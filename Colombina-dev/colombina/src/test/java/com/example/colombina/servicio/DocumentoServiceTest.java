package com.example.colombina.servicio;

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

import com.example.colombina.entidad.Documento;
import com.example.colombina.entidad.Tramite;
import com.example.colombina.repositorio.DocumentoRepository;

class DocumentoServiceTest {

    @Mock
    private DocumentoRepository documentoRepository;

    @InjectMocks
    private DocumentoService documentoService;

    private Tramite tramite;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tramite = new Tramite(1, "Tipo1", "Estado1", java.sql.Date.valueOf("2024-01-01"), java.sql.Date.valueOf("2024-02-01"), "Observaciones", java.sql.Date.valueOf("2024-03-01"), null, null);
    }

    @Test
    void saveDocumento_DeberiaGuardarDocumento() {
        Documento documento = new Documento("Documento de prueba", tramite);
        when(documentoRepository.save(any(Documento.class))).thenReturn(documento);

        Documento resultado = documentoService.saveDocumento(documento);

        assertNotNull(resultado);
        assertEquals("Documento de prueba", resultado.getInformacion());
        verify(documentoRepository, times(1)).save(documento);
    }

    @Test
    void getDocumentoById_DeberiaRetornarDocumentoSiExiste() {
        Documento documento = new Documento(1L, "Documento de prueba", tramite);
        when(documentoRepository.findById(1L)).thenReturn(Optional.of(documento));

        Optional<Documento> resultado = documentoService.getDocumentoById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Documento de prueba", resultado.get().getInformacion());
        verify(documentoRepository, times(1)).findById(1L);
    }

    @Test
    void getDocumentoById_DeberiaRetornarVacioSiNoExiste() {
        when(documentoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Documento> resultado = documentoService.getDocumentoById(1L);

        assertFalse(resultado.isPresent());
        verify(documentoRepository, times(1)).findById(1L);
    }

    @Test
    void getDocumentosByTramite_DeberiaRetornarListaDocumentos() {
        Documento documento1 = new Documento("Documento 1", tramite);
        Documento documento2 = new Documento("Documento 2", tramite);
        List<Documento> listaDocumentos = Arrays.asList(documento1, documento2);
        when(documentoRepository.findByTramite(any(Tramite.class))).thenReturn(listaDocumentos);

        List<Documento> resultado = documentoService.getDocumentosByTramite(tramite);

        assertEquals(2, resultado.size());
        verify(documentoRepository, times(1)).findByTramite(tramite);
    }

    @Test
    void getDocumentosByInformacionContaining_DeberiaRetornarListaDocumentos() {
        Documento documento1 = new Documento("Documento de prueba 1", tramite);
        Documento documento2 = new Documento("Documento de prueba 2", tramite);
        List<Documento> listaDocumentos = Arrays.asList(documento1, documento2);
        when(documentoRepository.findByInformacionContaining("prueba")).thenReturn(listaDocumentos);

        List<Documento> resultado = documentoService.getDocumentosByInformacionContaining("prueba");

        assertEquals(2, resultado.size());
        verify(documentoRepository, times(1)).findByInformacionContaining("prueba");
    }

    @Test
    void deleteDocumentoById_DeberiaEliminarDocumentoSiExiste() {
        doNothing().when(documentoRepository).deleteById(1L);

        documentoService.deleteDocumentoById(1L);

        verify(documentoRepository, times(1)).deleteById(1L);
    }

    @Test
    void getAllDocumentos_DeberiaRetornarTodosLosDocumentos() {
        Documento documento1 = new Documento("Documento 1", tramite);
        Documento documento2 = new Documento("Documento 2", tramite);
        List<Documento> listaDocumentos = Arrays.asList(documento1, documento2);
        when(documentoRepository.findAll()).thenReturn(listaDocumentos);

        List<Documento> resultado = documentoService.getAllDocumentos();

        assertEquals(2, resultado.size());
        verify(documentoRepository, times(1)).findAll();
    }
}
