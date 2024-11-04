package com.example.colombina.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestTramiteSolicitudDTO {
    private SolicitudDTO solicitudDTO;
    private TramiteDTO tramiteDTO;
}
