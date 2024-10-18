package com.example.colombina.DTOs;

import com.example.colombina.model.TipoPermiso;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermisoDTO {
    private Long id;
    private TipoPermiso tipoPermiso;
    private Long usuarioId;  // Referencia al Usuario
    private Long documentoId;  // Referencia al Documento
}

