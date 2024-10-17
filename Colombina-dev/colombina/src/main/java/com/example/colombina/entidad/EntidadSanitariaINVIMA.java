package com.example.colombina.entidad;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EntidadSanitariaINVIMA {
    @Id
    @GeneratedValue
    private Long idEntidad;
    private boolean resultadoTramite;
    private Date fechaRadicacion;
    private String comentarios;

    @OneToOne
    @JoinColumn(name = "idTramite")
    private Tramite tramite;
}
