package com.example.colombina.repositories;

import com.example.colombina.model.Usuario;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByRolTipoRolIn(List<String> roles);
}
