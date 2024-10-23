
package com.example.colombina.controlador;

import com.example.colombina.entidad.SolicitudDEI;
import com.example.colombina.servicio.SolicitudDEIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(SolicitudDEIController.class)
public class SolicitudDEIControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SolicitudDEIService solicitudDEIService;

    private SolicitudDEI solicitud1;
    private SolicitudDEI solicitud2;

    @BeforeEach
    void setUp() {
        solicitud1 = new SolicitudDEI(1L, "Juan Perez", new java.sql.Date(System.currentTimeMillis()), "Descripción 1", "Producto A", "Doc Técnica 1", "Registro A");
        solicitud2 = new SolicitudDEI(2L, "Maria Lopez", new java.sql.Date(System.currentTimeMillis()), "Descripción 2", "Producto B", "Doc Técnica 2", "Registro B");
    }

    @Test
    public void testCrearSolicitudDEI() throws Exception {
        Mockito.when(solicitudDEIService.crearSolicitudDEI(any(SolicitudDEI.class))).thenReturn(solicitud1);

        mockMvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombreSolicitante\": \"Juan Perez\", \"descripcion\": \"Descripción 1\", \"tipoDeProducto\": \"Producto A\", \"documentacionTecnica\": \"Doc Técnica 1\", \"tipoDeRegistro\": \"Registro A\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreSolicitante").value("Juan Perez"))
                .andExpect(jsonPath("$.descripcion").value("Descripción 1"));
    }

    @Test
    public void testObtenerTodasLasSolicitudesDEI() throws Exception {
        Mockito.when(solicitudDEIService.obtenerTodasLasSolicitudesDEI()).thenReturn(Arrays.asList(solicitud1, solicitud2));

        mockMvc.perform(get("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreSolicitante").value("Juan Perez"))
                .andExpect(jsonPath("$[1].nombreSolicitante").value("Maria Lopez"));
    }

    @Test
    public void testObtenerSolicitudDEIPorId() throws Exception {
        Mockito.when(solicitudDEIService.obtenerSolicitudDEIPorId(anyLong())).thenReturn(Optional.of(solicitud1));

        mockMvc.perform(get("/api/solicitudes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreSolicitante").value("Juan Perez"));
    }

    @Test
    public void testActualizarSolicitudDEI() throws Exception {
        Mockito.when(solicitudDEIService.actualizarSolicitudDEI(anyLong(), any(SolicitudDEI.class))).thenReturn(solicitud1);

        mockMvc.perform(put("/api/solicitudes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombreSolicitante\": \"Juan Perez\", \"descripcion\": \"Descripción 1\", \"tipoDeProducto\": \"Producto A\", \"documentacionTecnica\": \"Doc Técnica 1\", \"tipoDeRegistro\": \"Registro A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreSolicitante").value("Juan Perez"));
    }

    @Test
    public void testEliminarSolicitudDEI() throws Exception {
        Mockito.doNothing().when(solicitudDEIService).eliminarSolicitudDEI(anyLong());

        mockMvc.perform(delete("/api/solicitudes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testBuscarPorNombreSolicitante() throws Exception {
        Mockito.when(solicitudDEIService.buscarPorNombreSolicitante("Juan Perez")).thenReturn(Arrays.asList(solicitud1));

        mockMvc.perform(get("/api/solicitudes/nombre/Juan Perez")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreSolicitante").value("Juan Perez"));
    }

    @Test
    public void testBuscarPorTipoDeProducto() throws Exception {
        Mockito.when(solicitudDEIService.buscarPorTipoDeProducto("Producto A")).thenReturn(Arrays.asList(solicitud1));

        mockMvc.perform(get("/api/solicitudes/tipo/Producto A")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoDeProducto").value("Producto A"));
    }

    @Test
    public void testBuscarPorFechaSolicitud() throws Exception {
        Mockito.when(solicitudDEIService.buscarPorFechaSolicitud(any(java.util.Date.class))).thenReturn(Arrays.asList(solicitud1));

        mockMvc.perform(get("/api/solicitudes/fecha/" + solicitud1.getFechaSolicitud())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fechaSolicitud").value(solicitud1.getFechaSolicitud().toString()));
    }
}
