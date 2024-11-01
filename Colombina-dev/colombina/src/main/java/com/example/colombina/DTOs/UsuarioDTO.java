package com.example.colombina.DTOs;

import com.example.colombina.model.Rol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correoElectronico;
    private Rol rol;  // ADMIN, AGENTE, SOLICITANTE

    public UsuarioDTO(String username, String correoElectronico, String tipoRol) {
        this.nombre = username;
        this.correoElectronico = correoElectronico;
        this.rol = new Rol(tipoRol);
    }
}
