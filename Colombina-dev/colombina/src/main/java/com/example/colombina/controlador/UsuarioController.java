package com.example.colombina.controlador;

import java.util.List;
import java.util.Optional;

import com.example.colombina.config.TokenProvider;
import com.example.colombina.dto.UsuarioDTO;
import com.example.colombina.entidad.AuthToken;
import com.example.colombina.entidad.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.colombina.entidad.Usuario;
import com.example.colombina.servicio.UsuarioService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;
    
    @Autowired
    UsuarioService usuarioService;


    @RequestMapping(value = "/autenticacion", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getCorreo(),
                        loginUser.getContrasenia()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        return ResponseEntity.ok(new AuthToken(token));
    }

    /**
     * Saves a new user.
     *
     //* @param usuario The user to be saved.
     * @return The saved user.
     */
    @RequestMapping(value="/crear-usuario", method = RequestMethod.POST)
    public Usuario saveUser(@RequestBody UsuarioDTO usuario){
        return usuarioService.save(usuario);
    }

    /**
     * Returns a message that can only be accessed by users with the 'ADMIN' role.
     *
     * @return A message that can only be accessed by admins.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/adminping", method = RequestMethod.GET)
    public String adminPing(){
        return "Only Admins Can Read This";
    }

    /**
     * Returns a message that can be accessed by any user.
     *
     * @return A message that can be accessed by any user.
     */
    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/userping", method = RequestMethod.GET)
    public String userPing(){
        return "Any User Can Read This";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/crear/empleado", method = RequestMethod.POST)
    public Usuario crearEmpleado(@RequestBody UsuarioDTO usuario){
        return usuarioService.crearEmpleado(usuario);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/find/all", method = RequestMethod.GET)
    public List<Usuario> getAllList(){
        return usuarioService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/find/by/username", method = RequestMethod.GET)
    public Optional<Usuario> getAllList(@RequestParam String correo){
        return usuarioService.findOne(correo);
    }
}
