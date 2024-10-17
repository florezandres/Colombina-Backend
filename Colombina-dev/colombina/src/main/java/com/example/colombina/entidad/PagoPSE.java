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
public class PagoPSE {
    //Atributos
    @Id
    @GeneratedValue
    private Long idPagoPSE;

    private float monto;
    private Date fechaDePago;
    private String metodoDePago;
    private String referenciaDePago;

    //Relaciones

    @ManyToOne
    @JoinColumn(name = "idTramite")
    private Tramite tramite;
}
