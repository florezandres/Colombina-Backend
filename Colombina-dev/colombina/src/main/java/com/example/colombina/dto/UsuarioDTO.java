package com.example.colombina.dto;

import com.example.colombina.entidad.Rol;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correoElectronico;
    private Rol rol;  // ADMIN, AGENTE, SOLICITANTE
}
