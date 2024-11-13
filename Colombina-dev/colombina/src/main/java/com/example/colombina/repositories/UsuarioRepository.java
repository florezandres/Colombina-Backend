package com.example.colombina.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.colombina.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u JOIN u.solicitudes s WHERE s.tramite.id = :tramiteId")
    Usuario findSolicitanteByTramiteId(@Param("tramiteId") Long tramiteId);

    @Query("SELECT u FROM Usuario u JOIN u.solicitudes s WHERE s.id = :solicitudId")
    Usuario findSolicitanteBySolicitudId(@Param("solicitudId") Long solicitudId);

    Optional<Usuario> findByNombre(String nombre);

    List<Usuario> findByRolTipoRol(String tipoRol);

    Boolean existsByNombre(String nombre);
}
