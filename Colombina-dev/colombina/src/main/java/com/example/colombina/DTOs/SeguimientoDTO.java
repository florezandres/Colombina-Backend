package com.example.colombina.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SeguimientoDTO {
    private Long id;
    private String estadoActual;
    private Date fechaSeguimiento;
    private Long tramiteId;  // Referencia al Trámite
}

