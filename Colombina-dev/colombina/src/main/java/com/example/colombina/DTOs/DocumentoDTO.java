package com.example.colombina.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoDTO {
    private Long id;
    private String tipo;
    private boolean aprobado;
    private String urlTemp;  // Localización temporal del archivo
    private Long tramiteId;  // Referencia al Trámite
}

