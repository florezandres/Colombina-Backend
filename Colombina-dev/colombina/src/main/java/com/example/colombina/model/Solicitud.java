package com.example.colombina.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date fechaSolicitud;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "solicitante_id", nullable = false)
    @JsonIgnore
    private Usuario solicitante;

    @OneToOne
    @JoinColumn(name = "tramite_id", nullable = false)
    private Tramite tramite;
}

