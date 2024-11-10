package com.example.colombina.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SolicitudDTO {
    private Long id;
    private Date fechaSolicitud;
    private TramiteDTO tramite;
    private UsuarioDTO solicitante;
}

