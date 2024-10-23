package com.example.colombina.controlador;

import com.example.colombina.entidad.Tramite;
import com.example.colombina.servicio.TramiteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TramiteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private TramiteService tramiteService;

    @BeforeEach
    public void setUp() {
        tramiteService = Mockito.mock(TramiteService.class);
    }

    @Test
    public void crearTramite_Devuelve201_CuandoSeCreaConExito() throws Exception {
        Tramite tramite = new Tramite(1, "Tipo1", "Estado1", Date.valueOf("2024-01-01"), null, "Observaciones", null, null, null);
        
        when(tramiteService.crearTramite(any(Tramite.class))).thenReturn(tramite);
        
        mockMvc.perform(post("/api/tramites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(tramite)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroRegistro").value(1));
    }

    @Test
    public void obtenerTodosLosTramites_Devuelve200_CuandoHayTramites() throws Exception {
        Tramite tramite1 = new Tramite(1, "Tipo1", "Estado1", Date.valueOf("2024-01-01"), null, "Observaciones", null, null, null);
        Tramite tramite2 = new Tramite(2, "Tipo2", "Estado2", Date.valueOf("2024-01-02"), null, "Observaciones", null, null, null);

        when(tramiteService.obtenerTodosLosTramites()).thenReturn(Arrays.asList(tramite1, tramite2));
        
        mockMvc.perform(get("/api/tramites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroRegistro").value(1))
                .andExpect(jsonPath("$[1].numeroRegistro").value(2));
    }

    @Test
    public void obtenerTramitePorId_Devuelve200_CuandoSeEncuentraElTramite() throws Exception {
        Tramite tramite = new Tramite(1, "Tipo1", "Estado1", Date.valueOf("2024-01-01"), null, "Observaciones", null, null, null);
        
        when(tramiteService.obtenerTramitePorId(1L)).thenReturn(Optional.of(tramite));
        
        mockMvc.perform(get("/api/tramites/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroRegistro").value(1));
    }

    @Test
    public void obtenerTramitePorId_Devuelve404_CuandoNoSeEncuentraElTramite() throws Exception {
        when(tramiteService.obtenerTramitePorId(1L)).thenReturn(Optional.empty());
        
        mockMvc.perform(get("/api/tramites/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void actualizarTramite_Devuelve200_CuandoSeActualizaConExito() throws Exception {
        Tramite tramiteExistente = new Tramite(1, "Tipo1", "Estado1", Date.valueOf("2024-01-01"), null, "Observaciones", null, null, null);
        Tramite tramiteActualizado = new Tramite(1, "Tipo1", "EstadoActualizado", Date.valueOf("2024-01-01"), null, "Observaciones Actualizadas", null, null, null);
        
        when(tramiteService.obtenerTramitePorId(1L)).thenReturn(Optional.of(tramiteExistente));
        when(tramiteService.actualizarTramite(1L, tramiteActualizado)).thenReturn(tramiteActualizado);
        
        mockMvc.perform(put("/api/tramites/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(tramiteActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estadoTramite").value("EstadoActualizado"));
    }

    @Test
    public void eliminarTramite_Devuelve204_CuandoSeEliminaConExito() throws Exception {
        when(tramiteService.obtenerTramitePorId(1L)).thenReturn(Optional.of(new Tramite()));
        
        mockMvc.perform(delete("/api/tramites/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void eliminarTramite_Devuelve404_CuandoNoSeEncuentraElTramite() throws Exception {
        when(tramiteService.obtenerTramitePorId(1L)).thenReturn(Optional.empty());
        
        mockMvc.perform(delete("/api/tramites/1"))
                .andExpect(status().isNotFound());
    }
}
