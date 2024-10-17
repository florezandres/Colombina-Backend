package com.example.colombina.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    // Atributos
    @Id
    @GeneratedValue
    private Long idNotificacion;

    private String mensaje;
    private boolean estado;
    private String tipoNotificacion;
    

    // Relaciones
    @ManyToOne
    private Tramite tramite;
}