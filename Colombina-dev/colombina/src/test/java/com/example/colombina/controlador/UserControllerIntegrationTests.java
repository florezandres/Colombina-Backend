package com.example.colombina.controlador;

import com.example.colombina.entidad.Usuario;
import com.example.colombina.servicio.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllUsuarios() throws Exception {
        Usuario usuario1 = new Usuario("user1", "user1@example.com", "ROLE_USER", "password1");
        Usuario usuario2 = new Usuario("user2", "user2@example.com", "ROLE_USER", "password2");
        
        when(usuarioService.getAllUsuarios()).thenReturn(Arrays.asList(usuario1, usuario2));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].credencial").value("user1"))
                .andExpect(jsonPath("$[1].credencial").value("user2"));
    }

    @Test
    void getUsuarioById() throws Exception {
        Usuario usuario = new Usuario("user1", "user1@example.com", "ROLE_USER", "password1");
        usuario.setId(1L);

        when(usuarioService.getUsuarioById(anyLong())).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.credencial").value("user1"));
    }

    @Test
    void createUsuario() throws Exception {
        Usuario nuevoUsuario = new Usuario("user1", "user1@example.com", "ROLE_USER", "password1");
        
        when(usuarioService.saveUsuario(any())).thenReturn(nuevoUsuario);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoUsuario)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.credencial").value("user1"));
    }

    @Test
    void updateUsuario() throws Exception {
        Usuario usuarioExistente = new Usuario("user1", "user1@example.com", "ROLE_USER", "password1");
        usuarioExistente.setId(1L);
        
        when(usuarioService.getUsuarioById(anyLong())).thenReturn(Optional.of(usuarioExistente));
        when(usuarioService.saveUsuario(any())).thenReturn(usuarioExistente);

        Usuario usuarioActualizado = new Usuario("user1_updated", "user1@example.com", "ROLE_USER", "newPassword");
        
        mockMvc.perform(put("/api/usuarios/Update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioActualizado)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.credencial").value("user1_updated"));
    }

    @Test
    void deleteUsuarioById() throws Exception {
        Usuario usuario = new Usuario("user1", "user1@example.com", "ROLE_USER", "password1");
        usuario.setId(1L);

        when(usuarioService.getUsuarioById(anyLong())).thenReturn(Optional.of(usuario));

        mockMvc.perform(delete("/api/usuarios/Delete/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
