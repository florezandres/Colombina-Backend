package com.example.colombina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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

    @ManyToOne
    @JoinColumn(name = "tramite_id", nullable = false)
    private Tramite tramite;
}
