package com.example.colombina.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Documento {

    @Id
    @GeneratedValue
    private Long documentoId;

    // Aquí guardamos la ubicación del archivo temporalmente
    private String informacion;  // Este campo almacenará la ruta temporal del archivo

    // Relación con Tramite
    @ManyToOne
    @JoinColumn(name = "tramiteId", nullable = false)
    private Tramite tramite;
}
