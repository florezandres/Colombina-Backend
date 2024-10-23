package com.example.colombina.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioDTO {
    private Long id;
    private Long idUsuarioDestino;
    private Long idUsuarioOrigen;
    private String comentario;

}
