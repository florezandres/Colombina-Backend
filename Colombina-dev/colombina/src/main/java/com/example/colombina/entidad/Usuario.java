package com.example.colombina.entidad;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
public class Usuario {
    //Atributos
    @Id
    @GeneratedValue
    private Long id;
    
    private String credencial;
    private String correo;
    private String rol;
    private String contraseña;

    //RELACIONES E-R ANTERIOR
    @OneToMany(mappedBy="usuario", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Tramite> tramites = new ArrayList<>();

    //RELACIONES E-R NUEVO
    @OneToMany(mappedBy="usuario")
    private List<ExpedienteRegulatorio> expedientesRegulatorios = new ArrayList<>();
}

