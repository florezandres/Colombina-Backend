package com.example.colombina.DTOs;

import java.util.Date;

import lombok.Data;

@Data
public class InfoAceptacionEntidadSanitariaDTO {
    private Integer numExp;
    private String llaveRSA;
    private Date fechaRadicacion;
}
