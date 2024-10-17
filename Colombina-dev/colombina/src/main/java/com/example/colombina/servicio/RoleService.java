package com.example.colombina.servicio;


import com.example.colombina.entidad.Role;
import com.example.colombina.repositorio.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleService{

    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(String name) {
        // Find role by name using the roleDao
        Role role = roleRepository.findByName(name);
        return role;
    }
}