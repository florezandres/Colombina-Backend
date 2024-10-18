package com.example.colombina.init;

import com.example.colombina.model.EntidadSanitaria;
import com.example.colombina.model.Rol;
import com.example.colombina.model.Usuario;
import com.example.colombina.repositories.EntidadSanitariaRepository;
import com.example.colombina.repositories.RolRepository;
import com.example.colombina.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DBInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private EntidadSanitariaRepository entidadSanitariaRepository;

    @Override
    public void run(String... args) throws Exception {
        //Crear entidad
        EntidadSanitaria entidadSanitaria1 = new EntidadSanitaria(1L, "Entidad Sanitaria", "Colombia", new ArrayList<>());
        entidadSanitariaRepository.save(entidadSanitaria1);

        // Crear los roles
        Rol rolAdmin = new Rol("ADMIN");
        Rol rolAsuntosReg = new Rol("ASUNTOSREG");
        Rol rolSolicitante = new Rol("SOLICITANTE");
        Rol rolMercadeo = new Rol("MERCADEO");
        Rol rolExportaciones = new Rol("EXPORTACIONES");

        // Guardar los roles en la base de datos
        rolRepository.save(rolAdmin);
        rolRepository.save(rolAsuntosReg);
        rolRepository.save(rolSolicitante);
        rolRepository.save(rolMercadeo);
        rolRepository.save(rolExportaciones);

        // Crear los usuarios con los roles asignados
        Usuario admin = new Usuario(null, "Admin", "adminpass", "admin@example.com", rolAdmin, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Usuario asuntosReg = new Usuario(null, "AsuntosReg", "asuntosregpass", "asuntosreg@example.com", rolAsuntosReg, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Usuario solicitante = new Usuario(null, "Solicitante", "solicitantepass", "solicitante@example.com", rolSolicitante, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Usuario mercadeo = new Usuario(null, "Mercadeo", "mercadeopass", "mercadeo@example.com", rolMercadeo, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Usuario exportaciones = new Usuario(null, "Exportaciones", "exportacionespass", "exportaciones@example.com", rolExportaciones, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        // Guardar los usuarios en la base de datos
        usuarioRepository.save(admin);
        usuarioRepository.save(asuntosReg);
        usuarioRepository.save(solicitante);
        usuarioRepository.save(mercadeo);
        usuarioRepository.save(exportaciones);

        System.out.println("Usuarios inicializados correctamente.");
    }
}
