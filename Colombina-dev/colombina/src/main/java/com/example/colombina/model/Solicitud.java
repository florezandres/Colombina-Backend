package com.example.colombina.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    private Usuario solicitante;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tramite_id")
    private Tramite tramite;
}

