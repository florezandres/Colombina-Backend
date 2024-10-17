package com.example.colombina.entidad;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DocumentoTecnico {
    @Id
    @GeneratedValue
    private Long idDocumentoTecnico;

    private String tipoDocumento;
    private Date fechaAdjuncion;
    private String descripcion;
    private boolean estadoDocumento;

    
    @ManyToOne
    @JoinColumn(name = "idExpediente")
    private ExpedienteRegulatorio expedienteRegulatorio;
}

