package com.example.colombina.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.colombina.DTOs.LoginRequestDTO;
import com.example.colombina.services.AutenticacionService;
import com.example.colombina.services.NotificacionService;
import com.example.colombina.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/autenticacion")
public class AutenticacionController {

    @Autowired
    private AutenticacionService autenticacionService;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO body) {
        return ResponseEntity.ok(
            autenticacionService.login(body.getUsername(), body.getPassword())
        );
    }
    
    @PostMapping("/recuperar/{nombre}")
    public ResponseEntity<?> recuperarContraseña(@PathVariable String nombre) {
        if (!usuarioService.usuarioExistePorNombre(nombre)) {
            return ResponseEntity.badRequest().body("El usuario no existe");
        }

        notificacionService.recuperarContrasena(nombre);

        return ResponseEntity.ok(
            "Se ha enviado un correo con las instrucciones para recuperar la contraseña"
        );
    }

}
