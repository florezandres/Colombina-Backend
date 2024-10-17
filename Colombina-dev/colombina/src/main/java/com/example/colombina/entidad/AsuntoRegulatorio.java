package com.example.colombina.entidad;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AsuntoRegulatorio {

    @Id
    @GeneratedValue
    private Long idAsuntoRegulatorio;

    private String responsable;
    private boolean estadoAsunto;
    private Date fechaAsunto;

    // Relación con Solicitudes DEI
    @OneToMany(mappedBy = "asuntoRegulatorio")
    private List<Tramite> tramites = new ArrayList<>();
}
