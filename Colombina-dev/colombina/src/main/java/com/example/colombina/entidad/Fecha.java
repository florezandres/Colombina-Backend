package com.example.colombina.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fecha {
    //Atributos
    @Id
    @GeneratedValue
    private Long fechaId;

    private int dia;
    private int mes;
    private int año;

    //Relaciones
    @OneToOne(mappedBy = "fecha")
    private Tramite tramite;
}
