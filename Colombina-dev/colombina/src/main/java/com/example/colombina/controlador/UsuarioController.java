package com.example.colombina.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.colombina.entidad.Usuario;
import com.example.colombina.servicio.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {   
    
    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // GET: Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET: Obtener un usuario por credencial
    @GetMapping("/credencial/{credencial}")
    public ResponseEntity<Usuario> getUsuarioByCredencial(@PathVariable String credencial) {
        Optional<Usuario> usuario = usuarioService.getUsuarioByCredencial(credencial);
        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // GET: Obtener usuarios por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Usuario>> getUsuariosByTipo(@PathVariable String Rol) {
        List<Usuario> usuarios = usuarioService.getUsuariosByRol(Rol);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // POST: Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // PUT: Actualizar un usuario existente
    @PutMapping("/Update/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuarioOptional = usuarioService.getUsuarioById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setUsuario(usuarioActualizado.getUsuario());
            usuario.setCorreo(usuarioActualizado.getCorreo());
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setContraseña(usuarioActualizado.getContraseña());
            Usuario usuarioGuardado = usuarioService.saveUsuario(usuario);
            return new ResponseEntity<>(usuarioGuardado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE: Eliminar un usuario por ID
    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<Void> deleteUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.getUsuarioById(id);
        if (usuarioOptional.isPresent()) {
            usuarioService.deleteUsuarioById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
