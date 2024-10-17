package com.example.colombina.entidad;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
public class SolicitudDEI {

    @Id
    @GeneratedValue
    private Long idSolicitud;

    private Date fechaSolicitud;
    private String descripcion;
    private String tipoDeProducto;
    private String documentacionTecnica;  // Se refiere a una descripción general, no es la lista de documentos
    private String tipoDeRegistro;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "usuarioId", nullable = false)
    private Usuario usuario;  // Ya no es el nombre del solicitante en String, sino el Usuario

    // Relación con Tramite
    @OneToOne
    @JoinColumn(name = "tramiteId", nullable = false)
    private Tramite tramite;
}
