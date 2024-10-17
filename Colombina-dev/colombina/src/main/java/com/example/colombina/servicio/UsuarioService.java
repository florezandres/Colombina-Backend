package com.example.colombina.servicio;

import java.util.*;

import com.example.colombina.dto.UsuarioDTO;
import com.example.colombina.entidad.Role;
import com.example.colombina.repositorio.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.colombina.entidad.Usuario;
import com.example.colombina.repositorio.UsuarioRepository;

@Service (value = "userService")
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Autowired
    private ModelMapper modelMapper;


    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }


    /*public Optional<Usuario> getUsuarioByCredencial(String credencial) {
        return usuarioRepository.findByCredencial(credencial);
    }*/


    public Optional<Usuario> getUsuarioByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }


    /*public List<Usuario> getUsuariosByRol(String Rol) {
        return usuarioRepository.findByRol(Rol);
    }*/


    public void deleteUsuarioById(Long id) {
        usuarioRepository.deleteById(id);
    }


    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // Descomprimir el Optional<Usuario> o lanzar una excepción si no existe
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));

        // Crear un objeto User de Spring Security, pasando el correo, contraseña y roles
        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(),
                usuario.getContrasenia(),
                getAuthority(usuario));
    }

    // Get user authorities
    private Set<SimpleGrantedAuthority> getAuthority(Usuario usuario) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        // Acceder a los roles del usuario y agregar las autoridades correspondientes
        usuario.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });

        return authorities;
    }


    // Find all users
    public List<Usuario> findAll() {
        List<Usuario> list = new ArrayList<>();
        usuarioRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    // Find user by username
    public Optional<Usuario> findOne(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    // Save user
    public Usuario save(UsuarioDTO usuarioDTO) {
        Usuario nUsuario = modelMapper.map(usuarioDTO, Usuario.class);
        nUsuario.setContrasenia(bcryptEncoder.encode(usuarioDTO.getContrasenia()));

        // Set default role as USER
        Role role = roleRepository.findByName("EMPLEADO");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        // If email domain is admin.edu, add ADMIN role
        if(nUsuario.getContrasenia().split("@")[1].equals("admin.edu")){
            role = roleRepository.findByName("ADMIN");
            roleSet.add(role);
        }

        nUsuario.setRoles(roleSet);
        return usuarioRepository.save(nUsuario);
    }


    public Usuario crearEmpleado(UsuarioDTO usuarioDTO) {
        Usuario nUsuario = modelMapper.map(usuarioDTO, Usuario.class);
        nUsuario.setContrasenia(bcryptEncoder.encode(usuarioDTO.getContrasenia()));

        Role adminRole = roleRepository.findByName("ADMIN");
        Role empleadoRole = roleRepository.findByName("EMPLEADO");

        Set<Role> roleSet = new HashSet<>();
        if (empleadoRole != null) {
            roleSet.add(empleadoRole);
        }
        if (adminRole != null) {
            roleSet.add(adminRole);
        }

        nUsuario.setRoles(roleSet);
        return usuarioRepository.save(nUsuario);
    }
}