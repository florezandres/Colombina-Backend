package com.example.colombina.entidad;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ExpedienteRegulatorio {
    @Id
    @GeneratedValue
    private Long idExpediente;
    private int numeroExpediente;
    private Date fechaCreacion;
    private boolean estadoExpediente;
    private String descripcionExpediente;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idTramite")
    private Tramite tramite;

    @OneToMany(mappedBy = "idDocumentoTecnico")
    private List<DocumentoTecnico> documentoTecnico = new ArrayList<>();
}
