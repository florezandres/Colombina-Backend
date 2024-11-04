package com.example.colombina.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.colombina.DTOs.UsuarioDTO;
import com.example.colombina.model.Usuario;
import com.example.colombina.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{nombre}")
public ResponseEntity<UsuarioDTO> getUsuarioByNombre(@PathVariable String nombre) {
    UserDetailsService userDetailsService = usuarioService.userDetailsService();
    Usuario usuario = (Usuario) userDetailsService.loadUserByUsername(nombre); 
    UsuarioDTO usuarioDTO = new UsuarioDTO(
        usuario.getUsername(),
        usuario.getCorreoElectronico(),
        usuario.getRol().getTipoRol()
    );
    return ResponseEntity.ok(usuarioDTO);
}

}

