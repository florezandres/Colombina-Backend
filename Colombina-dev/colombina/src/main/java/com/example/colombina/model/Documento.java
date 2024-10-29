package com.example.colombina.model;

import java.time.LocalDate;

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
@Table(name = "documento")
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
    @ToString.Exclude
    @JoinColumn(name = "tramite_id", nullable = false)
    @JsonIgnore
    private Tramite tramite;

    @Column
    private LocalDate fechaExpiracion;

    @Column
    private boolean cumpleNormativas = true;

    // Constructor solo con id (para referencias rápidas)
    public Documento(Long id) {
        this.id = id;
    }

    // Verifica si el documento ha vencido
    public boolean isVencido() {
        return fechaExpiracion != null && fechaExpiracion.isBefore(LocalDate.now());
    }

    // Verifica si el documento cumple con las normativas
    public boolean isCumpleNormativas() {
        return cumpleNormativas;
    }

    // Obtener el tipo de documento
    public String getTipo() {
        return tipo;
    }
}
