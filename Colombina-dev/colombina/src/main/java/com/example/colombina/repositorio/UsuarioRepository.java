package com.example.colombina.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.colombina.entidad.Usuario;
public interface UsuarioRepository extends JpaRepository <Usuario, Long>{
    Optional<Usuario> findByCredencial(String Credencial);
    Optional<Usuario> findByCorreo(String correo);
    List<Usuario> findByRol(String Rol);
    
}
