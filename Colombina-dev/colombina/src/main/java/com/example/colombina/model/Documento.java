package com.example.colombina.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
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
@Table(name = "documento", uniqueConstraints = @UniqueConstraint(columnNames = {"nombre", "tramite_id"}))
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column
    private Boolean aprobado;

    @Column
    private String comentarios;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "tramite_id", nullable = false)
    @JsonIgnore
    private Tramite tramite;

    // Constructor solo con id (para referencias rápidas)
    public Documento(Long id) {
        this.id = id;
    }

    public Documento(String nombre, Boolean aprobado, Tramite tramite) {
        this.nombre = nombre;
        this.aprobado = aprobado;
        this.tramite = tramite;
    }
}
