package com.example.colombina.DTOs;

import com.example.colombina.model.Tramite;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class TramiteDTO {
    private Long id;
    private String numeroRadicado;
    private Tramite.EstadoTramite estado;
    private Date fechaRadicacion;
    private Long solicitanteId;  // Referencia al Usuario
    private Long entidadSanitariaId;  // Referencia a la EntidadSanitaria
    private Double progreso;
    private List<HistorialCambioDTO> historialCambioDTOList;

}

