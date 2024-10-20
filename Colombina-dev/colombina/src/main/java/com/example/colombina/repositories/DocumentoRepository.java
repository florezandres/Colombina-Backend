package com.example.colombina.repositories;

import com.example.colombina.model.Documento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    List<Documento> findByTramiteId(Long tramiteId);

}
