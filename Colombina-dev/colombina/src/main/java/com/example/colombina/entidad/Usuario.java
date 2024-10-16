package com.example.colombina.entidad;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

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

    //Constructores
    public Usuario(Long id, String credencial, String correo, String rol, String contraseña) {
        this.id = id;
        this.credencial = credencial;
        this.correo = correo;
        this.rol = rol;
        this.contraseña = contraseña;
    }

    public Usuario() {
    }

    public Usuario(String credencial, String correo, String rol, String contraseña) {
        this.credencial = credencial;
        this.correo = correo;
        this.rol = rol;
        this.contraseña = contraseña;
    }

    //Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return credencial;
    }

    public void setUsuario(String credencial) {
        this.credencial = credencial;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public List<Tramite> getTramites() {
        return tramites;
    }

    public void setTramites(List<Tramite> tramites) {
        this.tramites = tramites;
    }

    public List<ExpedienteRegulatorio> getExpedientesRegulatorios() {
        return expedientesRegulatorios;
    }

    public void setExpedientesRegulatorios(List<ExpedienteRegulatorio> expedientesRegulatorios) {
        this.expedientesRegulatorios = expedientesRegulatorios;
    }
}

