package com.example.colombina.DTOs;

import java.util.Date;

import lombok.Data;

@Data
public class InfoControlTramiteDTO {
    private Date fechaTerminacion;
    private Date fechaNotificacion;
    private Long idSeguimiento;
    private String registroSanitario;
    private String expedienteRSA;
    private Long numeroRSA;
    private Date fechaVencimientoRSA;
    private String planta;
    private String numeroFactura;
    private String observaciones;
}
