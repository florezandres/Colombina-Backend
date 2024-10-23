
package com.example.colombina.controlador;

import com.example.colombina.entidad.DocumentoTecnico;
import com.example.colombina.servicio.DocumentoTecnicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DocumentoTecnicoControllerTest {

    @InjectMocks
    private DocumentoTecnicoController documentoTecnicoController;

    @Mock
    private DocumentoTecnicoService documentoTecnicoService;

    private DocumentoTecnico documentoTecnico;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        documentoTecnico = new DocumentoTecnico("Tipo1", Date.valueOf("2023-01-01"), "Descripción", true);
    }

    @Test
    void crearDocumentoTecnico() {
        when(documentoTecnicoService.crearDocumentoTecnico(any(DocumentoTecnico.class))).thenReturn(documentoTecnico);

        ResponseEntity<DocumentoTecnico> response = documentoTecnicoController.crearDocumentoTecnico(documentoTecnico);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(documentoTecnico, response.getBody());
        verify(documentoTecnicoService, times(1)).crearDocumentoTecnico(any(DocumentoTecnico.class));
    }

    @Test
    void obtenerTodosLosDocumentosTecnicos() {
        when(documentoTecnicoService.obtenerTodosLosDocumentosTecnicos()).thenReturn(Collections.singletonList(documentoTecnico));

        ResponseEntity<List<DocumentoTecnico>> response = documentoTecnicoController.obtenerTodosLosDocumentosTecnicos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(documentoTecnico, response.getBody().get(0));
        verify(documentoTecnicoService, times(1)).obtenerTodosLosDocumentosTecnicos();
    }

    @Test
    void obtenerDocumentoTecnicoPorId() {
        when(documentoTecnicoService.obtenerDocumentoTecnicoPorId(1L)).thenReturn(Optional.of(documentoTecnico));

        ResponseEntity<DocumentoTecnico> response = documentoTecnicoController.obtenerDocumentoTecnicoPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(documentoTecnico, response.getBody());
        verify(documentoTecnicoService, times(1)).obtenerDocumentoTecnicoPorId(1L);
    }

    @Test
    void obtenerDocumentoTecnicoPorId_NotFound() {
        when(documentoTecnicoService.obtenerDocumentoTecnicoPorId(1L)).thenReturn(Optional.empty());

        ResponseEntity<DocumentoTecnico> response = documentoTecnicoController.obtenerDocumentoTecnicoPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(documentoTecnicoService, times(1)).obtenerDocumentoTecnicoPorId(1L);
    }

    @Test
    void actualizarDocumentoTecnico() {
        when(documentoTecnicoService.actualizarDocumentoTecnico(1L, documentoTecnico)).thenReturn(documentoTecnico);

        ResponseEntity<DocumentoTecnico> response = documentoTecnicoController.actualizarDocumentoTecnico(1L, documentoTecnico);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(documentoTecnico, response.getBody());
        verify(documentoTecnicoService, times(1)).actualizarDocumentoTecnico(1L, documentoTecnico);
    }

    @Test
    void eliminarDocumentoTecnico() {
        doNothing().when(documentoTecnicoService).eliminarDocumentoTecnico(1L);

        ResponseEntity<Void> response = documentoTecnicoController.eliminarDocumentoTecnico(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(documentoTecnicoService, times(1)).eliminarDocumentoTecnico(1L);
    }

    @Test
    void buscarPorTipoDocumento() {
        when(documentoTecnicoService.buscarPorTipoDocumento("Tipo1")).thenReturn(Collections.singletonList(documentoTecnico));

        ResponseEntity<List<DocumentoTecnico>> response = documentoTecnicoController.buscarPorTipoDocumento("Tipo1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(documentoTecnico, response.getBody().get(0));
        verify(documentoTecnicoService, times(1)).buscarPorTipoDocumento("Tipo1");
    }

    @Test
    void buscarPorFechaAdjuncion() {
        Date fecha = Date.valueOf("2023-01-01");
        when(documentoTecnicoService.buscarPorFechaAdjuncion(fecha)).thenReturn(Collections.singletonList(documentoTecnico));

        ResponseEntity<List<DocumentoTecnico>> response = documentoTecnicoController.buscarPorFechaAdjuncion(fecha);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(documentoTecnico, response.getBody().get(0));
        verify(documentoTecnicoService, times(1)).buscarPorFechaAdjuncion(fecha);
    }

    @Test
    void buscarPorEstadoDocumento() {
        when(documentoTecnicoService.buscarPorEstadoDocumento(true)).thenReturn(Collections.singletonList(documentoTecnico));

        ResponseEntity<List<DocumentoTecnico>> response = documentoTecnicoController.buscarPorEstadoDocumento(true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(documentoTecnico, response.getBody().get(0));
        verify(documentoTecnicoService, times(1)).buscarPorEstadoDocumento(true);
    }
}
