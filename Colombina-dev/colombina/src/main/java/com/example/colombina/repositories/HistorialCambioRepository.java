package com.example.colombina.repositories;

import com.example.colombina.model.HistorialCambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialCambioRepository extends JpaRepository<HistorialCambio, Long> {

}
