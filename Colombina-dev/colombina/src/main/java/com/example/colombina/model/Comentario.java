package com.example.colombina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_destino_id", referencedColumnName = "id")
    private Usuario usuarioDestino;

    @OneToOne
    @JoinColumn(name = "usuario_origen_id", referencedColumnName = "id")  // Corregir typo aquí
    private Usuario usuarioOrigen;

    @Column(length = 100)
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "historial_cambio_id", nullable = false)  // Asegurarse de que no sea null
    private HistorialCambio historialCambio;


}
