package com.example.colombina.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SolicitudDTO {
    private Long id;
    private String descripcionProducto;
    private String tipoProducto;
    private Date fechaSolicitud;
    private Long solicitanteId;  // Referencia al Usuario
}

