package com.example.colombina.DTOs;

import java.util.Date;

import lombok.Data;

@Data
public class InfoControlTramiteDTO {
    private Date fechaEnvioDocumnetos;
    private String idSeguimiento;
    private String observaciones;
}
