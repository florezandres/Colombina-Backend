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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String asunto;

    @Column(nullable = false)
    private String mensaje;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    @JsonIgnore
    private Usuario destinatario;

    // Nueva relación con Trámite
    @ManyToOne
    @JoinColumn(name = "tramite_id", nullable = false)
    @JsonIgnore
    private Tramite tramite;

    @Column(nullable = false)
    private boolean leida;

    public Notificacion(String asunto, String mensaje, Date fecha, Usuario destinatario, Tramite tramite, boolean leida) {
        this.asunto = asunto;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.destinatario = destinatario;
        this.tramite = tramite;
        this.leida = leida;
    }
}
