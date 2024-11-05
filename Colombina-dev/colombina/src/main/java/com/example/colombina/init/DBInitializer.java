package com.example.colombina.init;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.colombina.model.EntidadSanitaria;
import com.example.colombina.model.Rol;
import com.example.colombina.model.Usuario;
import com.example.colombina.repositories.EntidadSanitariaRepository;
import com.example.colombina.repositories.RolRepository;

import com.example.colombina.repositories.UsuarioRepository;

@Component
public class DBInitializer implements CommandLineRunner {

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private RolRepository rolRepository;

        @Autowired
        private EntidadSanitariaRepository entidadSanitariaRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        private static final Logger logger = LoggerFactory.getLogger(DBInitializer.class);

        @Override
        public void run(String... args) throws Exception {
                // Verificar si ya hay usuarios en la base de datos
                if (usuarioRepository.count() > 0) {
                        logger.info("Usuarios ya inicializados.");
                        return;
                }
                // Crear entidades
                EntidadSanitaria entidadColombia = new EntidadSanitaria("Entidad Sanitaria Colombia", "Colombia");
                entidadSanitariaRepository.save(entidadColombia);

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
                Usuario admin = new Usuario(1L, "Admin", passwordEncoder.encode("adminpass"), "admin@example.com",
                                rolAdmin,
                                new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                Usuario asuntosReg = new Usuario(2L, "AsuntosReg", passwordEncoder.encode("asuntosregpass"),
                                "asuntosreg@example.com", rolAsuntosReg, new ArrayList<>(), new ArrayList<>(),
                                new ArrayList<>());
                Usuario solicitante = new Usuario(3L, "Solicitante", passwordEncoder.encode("solicitantepass"),
                                "solicitante@example.com", rolSolicitante, new ArrayList<>(), new ArrayList<>(),
                                new ArrayList<>());
                Usuario mercadeo = new Usuario(4L, "Mercadeo", passwordEncoder.encode("mercadeopass"),
                                "mercadeo@example.com",
                                rolMercadeo, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                Usuario exportaciones = new Usuario(5L, "Exportaciones", passwordEncoder.encode("exportacionespass"),
                                "exportaciones@example.com", rolExportaciones, new ArrayList<>(), new ArrayList<>(),
                                new ArrayList<>());

                // Guardar los usuarios en la base de datos
                usuarioRepository.save(admin);
                usuarioRepository.save(asuntosReg);
                usuarioRepository.save(solicitante);
                usuarioRepository.save(mercadeo);
                usuarioRepository.save(exportaciones);

                logger.info("Usuarios inicializados correctamente.");
                // ************************************************************************************************************************************************
                // */
                // Datos para mostrar estadisticas
                EntidadSanitaria entidadBrasil = new EntidadSanitaria(2L, "Entidad Sanitaria Brasil", "Brasil",
                                new ArrayList<>());
                entidadSanitariaRepository.save(entidadBrasil);
                EntidadSanitaria entidadArgentina = new EntidadSanitaria(3L, "Entidad Sanitaria Argentina", "Argentina",
                                new ArrayList<>());
                entidadSanitariaRepository.save(entidadArgentina);
                EntidadSanitaria entidadChile = new EntidadSanitaria(4L, "Entidad Sanitaria Chile", "Chile",
                                new ArrayList<>());
                entidadSanitariaRepository.save(entidadChile);
                EntidadSanitaria entidadPeru = new EntidadSanitaria(5L, "Entidad Sanitaria Perú", "Perú",
                                new ArrayList<>());
                entidadSanitariaRepository.save(entidadPeru);

                Usuario solicitante1 = new Usuario(4L, "JuanPerez", passwordEncoder.encode("solicitantepass1"),
                                "juanperez@example.com", rolSolicitante, new ArrayList<>(), new ArrayList<>(),
                                new ArrayList<>());
                usuarioRepository.save(solicitante1);
                Usuario solicitante2 = new Usuario(5L, "AnaGomez", passwordEncoder.encode("solicitantepass2"),
                                "anagomez@example.com", rolSolicitante, new ArrayList<>(), new ArrayList<>(),
                                new ArrayList<>());
                usuarioRepository.save(solicitante2);
                Usuario solicitante3 = new Usuario(6L, "CarlosLopez", passwordEncoder.encode("solicitantepass3"),
                                "carloslopez@example.com", rolSolicitante, new ArrayList<>(), new ArrayList<>(),
                                new ArrayList<>());
                usuarioRepository.save(solicitante3);
                Usuario solicitante4 = new Usuario(7L, "LauraMartinez", passwordEncoder.encode("solicitantepass4"),
                                "lauramartinez@example.com", rolSolicitante, new ArrayList<>(), new ArrayList<>(),
                                new ArrayList<>());
                usuarioRepository.save(solicitante4);
                Usuario solicitante5 = new Usuario(8L, "PedroGarcia", passwordEncoder.encode("solicitantepass5"),
                                "PedroGarcia@example.com", rolSolicitante, new ArrayList<>(), new ArrayList<>(),
                                new ArrayList<>());
                usuarioRepository.save(solicitante5);

        }
}