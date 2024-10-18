package com.example.colombina.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HistorialCambioDTO {
    private Long id;
    private String descripcion;
    private Date fechaCambio;
    private Long tramiteId;  // Referencia al Trámite
}
