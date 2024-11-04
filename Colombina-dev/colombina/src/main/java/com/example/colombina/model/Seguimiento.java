package com.example.colombina.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seguimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String estadoActual;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaSeguimiento;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaInicioSeguimiento;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fechaFinSeguimiento;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "tramite_id", nullable = false)
    @JsonIgnore
    private Tramite tramite;

    @Transient  // No persistir este campo en la base de datos
    private long tiempoRevision;  // Tiempo de revisión en milisegundos
}
