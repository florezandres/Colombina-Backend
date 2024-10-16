package com.example.colombina.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.colombina.entidad.Fecha;

public interface FechaRepository extends JpaRepository <Fecha, Long>{
    List <Fecha> findByDia(int dia);
    List <Fecha> findByMes(int mes);
    List <Fecha> findByAño(int año);
    Optional<Fecha> findByDiaAndMesAndAño(int dia, int mes, int año);
}
