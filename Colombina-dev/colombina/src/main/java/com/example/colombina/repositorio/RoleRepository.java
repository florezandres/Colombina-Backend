package com.example.colombina.repositorio;
import com.example.colombina.entidad.Role;
import com.example.colombina.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
