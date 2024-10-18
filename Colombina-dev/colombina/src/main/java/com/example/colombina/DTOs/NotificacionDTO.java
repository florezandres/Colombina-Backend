package com.example.colombina.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificacionDTO {
    private Long id;
    private String mensaje;
    private Date fecha;
    private Long destinatarioId;  // Referencia al Usuario
}

