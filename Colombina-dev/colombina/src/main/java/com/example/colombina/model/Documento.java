package com.example.colombina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tipo;

    @Column
    private boolean aprobado;

    @Column(nullable = false)
    private String tempUrl;

    @ManyToOne
    @JoinColumn(name = "tramite_id", nullable = false)
    private TramiteRegulatorio tramite;
}
