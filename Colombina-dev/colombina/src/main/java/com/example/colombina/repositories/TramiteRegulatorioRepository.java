package com.example.colombina.repositories;

import com.example.colombina.model.TramiteRegulatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TramiteRegulatorioRepository extends JpaRepository<TramiteRegulatorio, Long> {
}
