package com.example.colombina.dto;

import com.example.colombina.entidad.TramiteRegulatorio;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TramiteRegulatorioDTO {
    private Long id;
    private String numeroRadicado;
    private TramiteRegulatorio.EstadoTramite estado;
    private Date fechaRadicacion;
    private Long solicitanteId;  // Referencia al Usuario
    private Long entidadSanitariaId;  // Referencia a la EntidadSanitaria
}

