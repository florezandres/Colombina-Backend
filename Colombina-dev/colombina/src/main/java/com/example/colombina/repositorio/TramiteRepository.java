package com.example.colombina.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.colombina.entidad.Tramite;
public interface TramiteRepository extends JpaRepository <Tramite, Long>{
    
}
