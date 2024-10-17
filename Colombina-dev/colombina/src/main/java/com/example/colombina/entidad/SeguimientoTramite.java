package com.example.colombina.entidad;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class SeguimientoTramite {
    @Id
    @GeneratedValue
    private Long idSeguimiento;
    private Date fechaSeguimiento;
    private String estadoActual;
    private String accionesRealizadas; //esto estaba como lista pero debe ser un atributo unitario para estar en la entidad. Replantear lógica
    private String observaciones;

    @OneToOne
    @JoinColumn(name = "idTramite")
    private Tramite tramite;
}
