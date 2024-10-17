package com.example.colombina.entidad;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
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
    private String correo;
    private String contrasenia;

    //RELACIONES E-R ANTERIOR
    @OneToMany(mappedBy="usuario", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Tramite> tramites = new ArrayList<>();

    //RELACIONES E-R NUEVO
    @OneToMany(mappedBy="usuario")
    private List<ExpedienteRegulatorio> expedientesRegulatorios = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;
}

