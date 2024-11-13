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
    private String nombreProducto;
    private String descripcionProducto;
    private String tipoProducto;
    private String tipoTramite;
    private Tramite.EstadoTramite estado;
    private Date fechaRadicacion;
    private Long solicitanteId;  // Referencia al Usuario
    private Long entidadSanitariaId;  // Referencia a la EntidadSanitaria
    private Double progreso;
    private Double llave;
    private String etapa;
    private String pt;
    private String unidadNegocio;
    private Integer numProyectoSap;
    private String proyecto;
    private String tipoModificacion;
    private String claseTramite;
    private List<HistorialCambioDTO> historialCambioDTOList;
    private Date fechaSolicitud;
    private EntidadSanitariaDTO entidadSanitaria;
    private Date fechaTerminacion;
    private Date fechaNotificacion;
    private String idSeguimiento;
    private String registroSanitario;
    private String expedienteRSA;
    private Long numeroRSA;
    private Date fechaVencimientoRSA;
    private String planta;
    private String numeroFactura;
    private String observaciones;
    private Boolean urgente;
    private Date fechaEnvioDocumentos;
}

