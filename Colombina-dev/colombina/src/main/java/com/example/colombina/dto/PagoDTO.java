package com.example.colombina.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class PagoDTO {
    private Long id;
    private float monto;
    private String referencia;
    private Date fechaPago;
    private Long tramiteId;  // Referencia al Trámite
}

