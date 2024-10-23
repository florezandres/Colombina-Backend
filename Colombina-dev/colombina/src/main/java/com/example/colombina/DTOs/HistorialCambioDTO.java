package com.example.colombina.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class HistorialCambioDTO {
    private Long id;
    private String descripcion;
    private Date fechaCambio;
    private Long tramiteId;  // Referencia al Trámite
    private List<ComentarioDTO> comentarios = new ArrayList<>();
}
