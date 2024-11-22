package com.example.colombina.DTOs;

import java.util.Date;

import lombok.Data;

@Data
public class InfoAperturaTramiteDTO {
    private String pt;
    private String unidadNegocio;
    private Integer numProyectoSap;
    private String proyecto;
    private String tipoModificacion;
    private String registroRSA;
    private String expedienteRSA;
    private Boolean urgente;
    private Integer numRSA;
    private Date vencimientoRSA;
    private String planta;
    private String observaciones;
}
