
package com.example.colombina.controlador;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.colombina.entidad.ArchivoControlDeTramites;
import com.example.colombina.servicio.ArchivoControlDeTramitesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.Optional;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(ArchivoControlDeTramitesController.class)
public class ArchivoControlDeTramitesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArchivoControlDeTramitesService archivoService;

    private ArchivoControlDeTramites archivo;

    @BeforeEach
    void setUp() {
        archivo = new ArchivoControlDeTramites(Date.valueOf("2024-10-22"), true, "Modificación 1");
    }

    @Test
    void testCrearArchivo() throws Exception {
        when(archivoService.crearArchivo(any(ArchivoControlDeTramites.class))).thenReturn(archivo);

        mockMvc.perform(post("/api/archivos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fechaCreacion\":\"2024-10-22\", \"estadoTramite\":true, \"historialDeModificaciones\":\"Modificación 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fechaCreacion").value("2024-10-22"))
                .andExpect(jsonPath("$.estadoTramite").value(true))
                .andExpect(jsonPath("$.historialDeModificaciones").value("Modificación 1"));
    }

    @Test
    void testObtenerTodosLosArchivos() throws Exception {
        List<ArchivoControlDeTramites> archivos = Arrays.asList(archivo);
        when(archivoService.obtenerTodosLosArchivos()).thenReturn(archivos);

        mockMvc.perform(get("/api/archivos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fechaCreacion").value("2024-10-22"))
                .andExpect(jsonPath("$[0].estadoTramite").value(true));
    }

    @Test
    void testObtenerArchivoPorId() throws Exception {
        when(archivoService.obtenerArchivoPorId(1L)).thenReturn(Optional.of(archivo));

        mockMvc.perform(get("/api/archivos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fechaCreacion").value("2024-10-22"))
                .andExpect(jsonPath("$.estadoTramite").value(true));
    }

    @Test
    void testObtenerArchivoPorId_NotFound() throws Exception {
        when(archivoService.obtenerArchivoPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/archivos/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarArchivo() throws Exception {
        ArchivoControlDeTramites archivoActualizado = new ArchivoControlDeTramites(Date.valueOf("2024-10-23"), false, "Modificación 2");
        when(archivoService.actualizarArchivo(eq(1L), any(ArchivoControlDeTramites.class))).thenReturn(archivoActualizado);

        mockMvc.perform(put("/api/archivos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fechaCreacion\":\"2024-10-23\", \"estadoTramite\":false, \"historialDeModificaciones\":\"Modificación 2\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fechaCreacion").value("2024-10-23"))
                .andExpect(jsonPath("$.estadoTramite").value(false))
                .andExpect(jsonPath("$.historialDeModificaciones").value("Modificación 2"));
    }

    @Test
    void testEliminarArchivo() throws Exception {
        doNothing().when(archivoService).eliminarArchivo(1L);

        mockMvc.perform(delete("/api/archivos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testBuscarPorEstadoTramite() throws Exception {
        List<ArchivoControlDeTramites> archivos = Arrays.asList(archivo);
        when(archivoService.buscarPorEstadoTramite(true)).thenReturn(archivos);

        mockMvc.perform(get("/api/archivos/estado/true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estadoTramite").value(true));
    }

    @Test
    void testBuscarPorFechaCreacion() throws Exception {
        List<ArchivoControlDeTramites> archivos = Arrays.asList(archivo);
        when(archivoService.buscarPorFechaCreacion(Date.valueOf("2024-10-22"))).thenReturn(archivos);

        mockMvc.perform(get("/api/archivos/fecha/2024-10-22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fechaCreacion").value("2024-10-22"));
    }

    @Test
    void testBuscarPorHistorialDeModificaciones() throws Exception {
        List<ArchivoControlDeTramites> archivos = Arrays.asList(archivo);
        when(archivoService.buscarPorHistorialDeModificaciones("Modificación")).thenReturn(archivos);

        mockMvc.perform(get("/api/archivos/historial/Modificación"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].historialDeModificaciones").value("Modificación 1"));
    }
}
