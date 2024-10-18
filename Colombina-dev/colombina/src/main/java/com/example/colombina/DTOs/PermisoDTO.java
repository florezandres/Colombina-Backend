package com.example.colombina.DTOs;

import com.example.colombina.model.Permiso;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermisoDTO {
    private Long id;
    private Permiso.TipoPermiso tipoPermiso;
    private Long usuarioId;  // Referencia al Usuario
    private Long documentoId;  // Referencia al Documento
}

