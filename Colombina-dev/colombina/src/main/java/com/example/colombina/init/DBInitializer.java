package com.example.colombina.init;

import com.example.colombina.model.*;
import com.example.colombina.repositories.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class DBInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private EntidadSanitariaRepository entidadSanitariaRepository;

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private TramiteRepository tramiteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(DBInitializer.class);

    @Override
    public void run(String... args) throws Exception {
        // Crear entidad
        EntidadSanitaria entidadSanitaria1 = new EntidadSanitaria(1L, "Entidad Sanitaria", "Colombia",
                new ArrayList<>());
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
        Usuario admin = new Usuario(1L, "Admin", passwordEncoder.encode("adminpass"), "admin@example.com", rolAdmin,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Usuario asuntosReg = new Usuario(2L, "AsuntosReg", passwordEncoder.encode("asuntosregpass"),
                "asuntosreg@example.com", rolAsuntosReg, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Usuario solicitante = new Usuario(3L, "Solicitante", passwordEncoder.encode("solicitantepass"),
                "solicitante@example.com", rolSolicitante, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Usuario mercadeo = new Usuario(4L, "Mercadeo", passwordEncoder.encode("mercadeopass"), "mercadeo@example.com",
                rolMercadeo, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        Usuario exportaciones = new Usuario(5L, "Exportaciones", passwordEncoder.encode("exportacionespass"),
                "exportaciones@example.com", rolExportaciones, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        // Guardar los usuarios en la base de datos
        usuarioRepository.save(admin);
        usuarioRepository.save(asuntosReg);
        usuarioRepository.save(solicitante);
        usuarioRepository.save(mercadeo);
        usuarioRepository.save(exportaciones);

        logger.info("Usuarios inicializados correctamente.");

        Date date = new Date();


        Solicitud solicitud1 = new Solicitud(null, date, solicitante);
        Solicitud solicitud2 = new Solicitud(null,date,solicitante);
        solicitudRepository.save(solicitud1);
        solicitudRepository.save(solicitud2);

        Tramite tramite1 = new Tramite("AR-1", "Nombre 1", "Descripcion 1", "Tipo 1", Tramite.EstadoTramite.EN_REVISION, date, Tramite.TipoTramite.NACIONAL, 5.0, entidadSanitaria1, solicitud1);
        Tramite tramite2 = new Tramite("AR-2", "Nombre 2", "Descripcion 2", "Tipo 2", Tramite.EstadoTramite.APROBADO, date, Tramite.TipoTramite.INTERNACIONAL, 8.0, entidadSanitaria1, solicitud2);
        tramiteRepository.save(tramite1);
        tramiteRepository.save(tramite2);
    }
}