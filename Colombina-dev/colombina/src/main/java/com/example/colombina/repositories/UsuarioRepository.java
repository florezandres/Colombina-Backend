package com.example.colombina.repositories;

import com.example.colombina.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u JOIN u.solicitudes s WHERE s.id = :tramiteId")
    Usuario findSolicitanteByTramiteId(@Param("tramiteId") Long tramiteId);

    Optional<Usuario> findByNombre(String nombre);
}
