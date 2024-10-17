package com.example.colombina.entidad;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ArchivoControlDeTramites {
    @Id
    @GeneratedValue
    private Long idArchivoControlDeTramites;
    
    private Date fechaCreacion;
    private boolean estadoTramite;
    private String historialDeModificaciones;

    @OneToMany(mappedBy = "archivoControlDeTramites")
    private List<Tramite> tramites = new ArrayList<>();
}