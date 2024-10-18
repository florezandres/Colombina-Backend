package com.example.colombina.dto;

import com.example.colombina.entidad.TipoPermiso;
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

