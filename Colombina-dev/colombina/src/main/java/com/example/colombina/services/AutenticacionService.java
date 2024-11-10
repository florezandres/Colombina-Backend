package com.example.colombina.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.colombina.DTOs.LoginResponseDTO;
import com.example.colombina.model.Usuario;
import com.example.colombina.repositories.UsuarioRepository;

@Service
public class AutenticacionService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponseDTO login(String username, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByNombre(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        String token = jwtService.generateToken(usuario.get());
        return new LoginResponseDTO(token, username, usuario.get().getRol().getTipoRol());
    }

    public LoginResponseDTO refresh(String token) {
        token = token.substring(7);
        String username = jwtService.extractUserName(token);
        Optional<Usuario> usuario = usuarioRepository.findByNombre(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        token = jwtService.generateToken(usuario.get());

        return new LoginResponseDTO(token, username, usuario.get().getRol().getTipoRol());
    }

}
