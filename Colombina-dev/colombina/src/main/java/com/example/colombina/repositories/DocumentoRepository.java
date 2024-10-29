package com.example.colombina.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.colombina.model.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findByTramiteId(Long tramiteId);

}
