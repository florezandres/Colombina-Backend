package com.example.colombina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EntidadSanitaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String pais;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "entidadSanitaria", cascade = CascadeType.ALL)
    private List<Tramite>  tramites = new ArrayList<>();

    public EntidadSanitaria(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
        this.tramites = new ArrayList<>();
    }
}
