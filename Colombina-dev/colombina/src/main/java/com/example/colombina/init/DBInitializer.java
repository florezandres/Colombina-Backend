package com.example.colombina.init;

import com.example.colombina.model.*;
import com.example.colombina.repositories.*;
import java.util.Calendar;
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

                Calendar cal = Calendar.getInstance();
                cal.set(2023, Calendar.OCTOBER, 10);
                Date fechaoctubre2024 = cal.getTime();

                Tramite tramite = new Tramite("AR-1", "Nombre 1", "Descripcion 1", "Tipo 1",
                                Tramite.EstadoTramite.EN_REVISION, fechaoctubre2024, Tramite.TipoTramite.NACIONAL, 5,
                                entidadColombia, null);
                tramiteRepository.save(tramite);

                Solicitud solicitud = new Solicitud(null, fechaoctubre2024, solicitante, tramite);
                solicitudRepository.save(solicitud);
                tramite.setSolicitud(solicitud);
                tramiteRepository.save(tramite);
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

                cal.set(2023, Calendar.OCTOBER, 10);
                Date fechaOctubre2023 = cal.getTime();
                cal.set(2023, Calendar.NOVEMBER, 15);
                Date fechaNoviembre2023 = cal.getTime();
                cal.set(2023, Calendar.DECEMBER, 20);
                Date fechaDiciembre2023 = cal.getTime();
                cal.set(2024, Calendar.JANUARY, 5);
                Date fechaEnero2024 = cal.getTime();
                cal.set(2024, Calendar.FEBRUARY, 14);
                Date fechaFebrero2024 = cal.getTime();
                cal.set(2024, Calendar.MARCH, 25);
                Date fechaMarzo2024 = cal.getTime();
                cal.set(2024, Calendar.APRIL, 10);
                Date fechaAbril2024 = cal.getTime();
                cal.set(2024, Calendar.MAY, 30);
                Date fechaMayo2024 = cal.getTime();

                Tramite tramite2 = new Tramite("AR-2", "Producto 2", "Descripción 2", "Tipo 2",
                                Tramite.EstadoTramite.APROBADO, fechaNoviembre2023, Tramite.TipoTramite.INTERNACIONAL,
                                1, entidadChile, null);
                tramiteRepository.save(tramite2);
                Tramite tramite3 = new Tramite("AR-3", "Producto 3", "Descripción 3", "Tipo 3",
                                Tramite.EstadoTramite.APROBADO, fechaNoviembre2023, Tramite.TipoTramite.NACIONAL, 1,
                                entidadArgentina, null);
                tramiteRepository.save(tramite3);
                Tramite tramite4 = new Tramite("AR-4", "Producto 4", "Descripción 4", "Tipo 4",
                                Tramite.EstadoTramite.APROBADO, fechaNoviembre2023, Tramite.TipoTramite.INTERNACIONAL,
                                2, entidadBrasil, null);
                tramiteRepository.save(tramite4);
                Tramite tramite5 = new Tramite("AR-5", "Producto 5", "Descripción 5", "Tipo 5",
                                Tramite.EstadoTramite.APROBADO, fechaFebrero2024, Tramite.TipoTramite.NACIONAL, 2,
                                entidadPeru, null);
                tramiteRepository.save(tramite5);
                Tramite tramite6 = new Tramite("AR-6", "Producto 6", "Descripción 6", "Tipo 5",
                                Tramite.EstadoTramite.APROBADO, fechaFebrero2024, Tramite.TipoTramite.INTERNACIONAL,
                                3, entidadChile, null);
                tramiteRepository.save(tramite6);
                Tramite tramite7 = new Tramite("AR-7", "Producto 7", "Descripción 7", "Tipo 5",
                                Tramite.EstadoTramite.RECHAZADO, fechaFebrero2024, Tramite.TipoTramite.NACIONAL, 3,
                                entidadArgentina, null);
                tramiteRepository.save(tramite7);
                Tramite tramite8 = new Tramite("AR-8", "Producto 8", "Descripción 8", "Tipo 5",
                                Tramite.EstadoTramite.PENDIENTE, fechaFebrero2024, Tramite.TipoTramite.INTERNACIONAL,
                                4, entidadBrasil, null);
                tramiteRepository.save(tramite8);
                Tramite tramite9 = new Tramite("AR-9", "Producto 9", "Descripción 9", "Tipo 5",
                                Tramite.EstadoTramite.EN_REVISION, fechaFebrero2024, Tramite.TipoTramite.NACIONAL, 4,
                                entidadPeru, null);
                tramiteRepository.save(tramite9);
                Tramite tramite10 = new Tramite("AR-10", "Producto 10", "Descripción 10", "Tipo 5",
                                Tramite.EstadoTramite.RECHAZADO, fechaNoviembre2023, Tramite.TipoTramite.INTERNACIONAL,
                                5, entidadChile, null);
                tramiteRepository.save(tramite10);
                Tramite tramite11 = new Tramite("AR-11", "Producto 11", "Descripción 11", "Tipo 1",
                                Tramite.EstadoTramite.RECHAZADO, fechaDiciembre2023, Tramite.TipoTramite.NACIONAL, 5,
                                entidadArgentina, null);
                tramiteRepository.save(tramite11);
                Tramite tramite12 = new Tramite("AR-12", "Producto 12", "Descripción 12", "Tipo 1",
                                Tramite.EstadoTramite.PENDIENTE, fechaEnero2024, Tramite.TipoTramite.INTERNACIONAL,
                                6, entidadBrasil, null);
                tramiteRepository.save(tramite12);
                Tramite tramite13 = new Tramite("AR-13", "Producto 13", "Descripción 13", "Tipo 1",
                                Tramite.EstadoTramite.EN_REVISION, fechaFebrero2024, Tramite.TipoTramite.NACIONAL, 6,
                                entidadPeru, null);
                tramiteRepository.save(tramite13);
                Tramite tramite14 = new Tramite("AR-14", "Producto 14", "Descripción 14", "Tipo 1",
                                Tramite.EstadoTramite.APROBADO, fechaMarzo2024, Tramite.TipoTramite.INTERNACIONAL, 7,
                                entidadChile, null);
                tramiteRepository.save(tramite14);
                Tramite tramite15 = new Tramite("AR-15", "Producto 15", "Descripción 15", "Tipo 1",
                                Tramite.EstadoTramite.RECHAZADO, fechaAbril2024, Tramite.TipoTramite.NACIONAL, 7,
                                entidadArgentina, null);
                tramiteRepository.save(tramite15);
                Tramite tramite16 = new Tramite("AR-16", "Producto 16", "Descripción 16", "Tipo 1",
                                Tramite.EstadoTramite.PENDIENTE, fechaMayo2024, Tramite.TipoTramite.INTERNACIONAL, 7,
                                entidadBrasil, null);
                tramiteRepository.save(tramite16);
                Tramite tramite17 = new Tramite("AR-17", "Producto 17", "Descripción 17", "Tipo 4",
                                Tramite.EstadoTramite.EN_REVISION, fechaOctubre2023, Tramite.TipoTramite.NACIONAL, 8,
                                entidadPeru, null);
                tramiteRepository.save(tramite17);
                Tramite tramite18 = new Tramite("AR-18", "Producto 18", "Descripción 18", "Tipo 4",
                                Tramite.EstadoTramite.EN_REVISION, fechaNoviembre2023,
                                Tramite.TipoTramite.INTERNACIONAL, 8, entidadChile, null);
                tramiteRepository.save(tramite18);
                Tramite tramite19 = new Tramite("AR-19", "Producto 19", "Descripción 19", "Tipo 4",
                                Tramite.EstadoTramite.RECHAZADO, fechaDiciembre2023, Tramite.TipoTramite.NACIONAL, 9,
                                entidadArgentina, null);
                tramiteRepository.save(tramite19);
                Tramite tramite20 = new Tramite("AR-20", "Producto 20", "Descripción 20", "Tipo 4",
                                Tramite.EstadoTramite.PENDIENTE, fechaEnero2024, Tramite.TipoTramite.INTERNACIONAL,
                                8, entidadBrasil, null);
                tramiteRepository.save(tramite20);
                Tramite tramite21 = new Tramite("AR-21", "Producto 21", "Descripción 21", "Tipo 4",
                                Tramite.EstadoTramite.EN_REVISION, fechaFebrero2024, Tramite.TipoTramite.INTERNACIONAL,
                                7, entidadPeru, null);
                tramiteRepository.save(tramite21);
                Tramite tramite22 = new Tramite("AR-22", "Producto 22", "Descripción 22", "Tipo 4",
                                Tramite.EstadoTramite.EN_REVISION, fechaMarzo2024, Tramite.TipoTramite.INTERNACIONAL,
                                8, entidadChile, null);
                tramiteRepository.save(tramite22);
                Tramite tramite23 = new Tramite("AR-23", "Producto 23", "Descripción 23", "Tipo 2",
                                Tramite.EstadoTramite.RECHAZADO, fechaAbril2024, Tramite.TipoTramite.NACIONAL, 9,
                                entidadArgentina, null);
                tramiteRepository.save(tramite23);
                Tramite tramite24 = new Tramite("AR-24", "Producto 24", "Descripción 24", "Tipo 2",
                                Tramite.EstadoTramite.PENDIENTE, fechaMayo2024, Tramite.TipoTramite.INTERNACIONAL,
                                8, entidadBrasil, null);
                tramiteRepository.save(tramite24);
                Tramite tramite25 = new Tramite("AR-25", "Producto 25", "Descripción 25", "Tipo 2",
                                Tramite.EstadoTramite.EN_REVISION, fechaMarzo2024, Tramite.TipoTramite.INTERNACIONAL,
                                8, entidadPeru, null);
                tramiteRepository.save(tramite25);
                Tramite tramite26 = new Tramite("AR-26", "Producto 26", "Descripción 26", "Tipo 2",
                                Tramite.EstadoTramite.EN_REVISION, fechaDiciembre2023,
                                Tramite.TipoTramite.INTERNACIONAL, 7, entidadChile, null);
                tramiteRepository.save(tramite26);
                Tramite tramite27 = new Tramite("AR-27", "Producto 27", "Descripción 27", "Tipo 2",
                                Tramite.EstadoTramite.RECHAZADO, fechaDiciembre2023, Tramite.TipoTramite.INTERNACIONAL,
                                5, entidadArgentina, null);
                tramiteRepository.save(tramite27);
                Tramite tramite28 = new Tramite("AR-28", "Producto 28", "Descripción 28", "Tipo 2",
                                Tramite.EstadoTramite.PENDIENTE, fechaDiciembre2023, Tramite.TipoTramite.INTERNACIONAL,
                                8, entidadBrasil, null);
                tramiteRepository.save(tramite28);
                Tramite tramite29 = new Tramite("AR-29", "Producto 29", "Descripción 29", "Tipo 2",
                                Tramite.EstadoTramite.EN_REVISION, fechaFebrero2024, Tramite.TipoTramite.INTERNACIONAL,
                                7, entidadBrasil, null);
                tramiteRepository.save(tramite29);
                Tramite tramite30 = new Tramite("AR-30", "Producto 30", "Descripción 30", "Tipo 3",
                                Tramite.EstadoTramite.APROBADO, fechaMarzo2024, Tramite.TipoTramite.INTERNACIONAL,
                                6, entidadBrasil, null);
                tramiteRepository.save(tramite30);
                Tramite tramite31 = new Tramite("AR-31", "Producto 31", "Descripción 31", "Tipo 3",
                                Tramite.EstadoTramite.EN_REVISION, fechaDiciembre2023, Tramite.TipoTramite.NACIONAL,
                                9, entidadBrasil, null);
                tramiteRepository.save(tramite31);

                Solicitud solicitud2 = new Solicitud(null, fechaOctubre2023, solicitante2, tramite2);
                solicitudRepository.save(solicitud2);
                tramite2.setSolicitud(solicitud2);
                tramiteRepository.save(tramite2);
                Solicitud solicitud3 = new Solicitud(null, fechaOctubre2023, solicitante3, tramite3);
                solicitudRepository.save(solicitud3);
                tramite3.setSolicitud(solicitud3);
                tramiteRepository.save(tramite3);
                Solicitud solicitud4 = new Solicitud(null, fechaEnero2024, solicitante4, tramite4);
                solicitudRepository.save(solicitud4);
                tramite4.setSolicitud(solicitud4);
                tramiteRepository.save(tramite4);
                Solicitud solicitud5 = new Solicitud(null, fechaFebrero2024, solicitante1, tramite5);
                solicitudRepository.save(solicitud5);
                tramite5.setSolicitud(solicitud5);
                tramiteRepository.save(tramite5);
                Solicitud solicitud6 = new Solicitud(null, fechaMarzo2024, solicitante2, tramite6);
                solicitudRepository.save(solicitud6);
                tramite6.setSolicitud(solicitud6);
                tramiteRepository.save(tramite6);
                Solicitud solicitud7 = new Solicitud(null, fechaAbril2024, solicitante3, tramite7);
                solicitudRepository.save(solicitud7);
                tramite7.setSolicitud(solicitud7);
                tramiteRepository.save(tramite7);
                Solicitud solicitud8 = new Solicitud(null, fechaMarzo2024, solicitante4, tramite8);
                solicitudRepository.save(solicitud8);
                tramite8.setSolicitud(solicitud8);
                tramiteRepository.save(tramite8);
                Solicitud solicitud9 = new Solicitud(null, fechaOctubre2023, solicitante1, tramite9);
                solicitudRepository.save(solicitud9);
                tramite9.setSolicitud(solicitud9);
                tramiteRepository.save(tramite9);
                Solicitud solicitud10 = new Solicitud(null, fechaNoviembre2023, solicitante2, tramite10);
                solicitudRepository.save(solicitud10);
                tramite10.setSolicitud(solicitud10);
                tramiteRepository.save(tramite10);
                Solicitud solicitud11 = new Solicitud(null, fechaMarzo2024, solicitante3, tramite11);
                solicitudRepository.save(solicitud11);
                tramite11.setSolicitud(solicitud11);
                tramiteRepository.save(tramite11);
                Solicitud solicitud12 = new Solicitud(null, fechaEnero2024, solicitante4, tramite12);
                solicitudRepository.save(solicitud12);
                tramite12.setSolicitud(solicitud12);
                tramiteRepository.save(tramite12);
                Solicitud solicitud13 = new Solicitud(null, fechaFebrero2024, solicitante1, tramite13);
                solicitudRepository.save(solicitud13);
                tramite13.setSolicitud(solicitud13);
                tramiteRepository.save(tramite13);
                Solicitud solicitud14 = new Solicitud(null, fechaMarzo2024, solicitante2, tramite14);
                solicitudRepository.save(solicitud14);
                tramite14.setSolicitud(solicitud14);
                tramiteRepository.save(tramite14);
                Solicitud solicitud15 = new Solicitud(null, fechaFebrero2024, solicitante3, tramite15);
                solicitudRepository.save(solicitud15);
                tramite15.setSolicitud(solicitud15);
                tramiteRepository.save(tramite15);
                Solicitud solicitud16 = new Solicitud(null, fechaMarzo2024, solicitante4, tramite16);
                solicitudRepository.save(solicitud16);
                tramite16.setSolicitud(solicitud16);
                tramiteRepository.save(tramite16);
                Solicitud solicitud17 = new Solicitud(null, fechaOctubre2023, solicitante1, tramite17);
                solicitudRepository.save(solicitud17);
                tramite17.setSolicitud(solicitud17);
                tramiteRepository.save(tramite17);
                Solicitud solicitud18 = new Solicitud(null, fechaNoviembre2023, solicitante2, tramite18);
                solicitudRepository.save(solicitud18);
                tramite18.setSolicitud(solicitud18);
                tramiteRepository.save(tramite18);
                Solicitud solicitud19 = new Solicitud(null, fechaDiciembre2023, solicitante3, tramite19);
                solicitudRepository.save(solicitud19);
                tramite19.setSolicitud(solicitud19);
                tramiteRepository.save(tramite19);
                Solicitud solicitud20 = new Solicitud(null, fechaEnero2024, solicitante4, tramite20);
                solicitudRepository.save(solicitud20);
                tramite20.setSolicitud(solicitud20);
                tramiteRepository.save(tramite20);
                Solicitud solicitud21 = new Solicitud(null, fechaMarzo2024, solicitante1, tramite21);
                solicitudRepository.save(solicitud21);
                tramite21.setSolicitud(solicitud21);
                tramiteRepository.save(tramite21);
                Solicitud solicitud22 = new Solicitud(null, fechaMarzo2024, solicitante2, tramite22);
                solicitudRepository.save(solicitud22);
                tramite22.setSolicitud(solicitud22);
                tramiteRepository.save(tramite22);
                Solicitud solicitud23 = new Solicitud(null, fechaOctubre2023, solicitante3, tramite23);
                solicitudRepository.save(solicitud23);
                tramite23.setSolicitud(solicitud23);
                tramiteRepository.save(tramite23);
                Solicitud solicitud24 = new Solicitud(null, fechaMayo2024, solicitante4, tramite24);
                solicitudRepository.save(solicitud24);
                tramite24.setSolicitud(solicitud24);
                tramiteRepository.save(tramite24);
                Solicitud solicitud25 = new Solicitud(null, fechaOctubre2023, solicitante1, tramite25);
                solicitudRepository.save(solicitud25);
                tramite25.setSolicitud(solicitud25);
                tramiteRepository.save(tramite25);
                Solicitud solicitud26 = new Solicitud(null, fechaMarzo2024, solicitante2, tramite26);
                solicitudRepository.save(solicitud26);
                tramite26.setSolicitud(solicitud26);
                tramiteRepository.save(tramite26);
                Solicitud solicitud27 = new Solicitud(null, fechaDiciembre2023, solicitante3, tramite27);
                solicitudRepository.save(solicitud27);
                tramite27.setSolicitud(solicitud27);
                tramiteRepository.save(tramite27);
                Solicitud solicitud28 = new Solicitud(null, fechaEnero2024, solicitante4, tramite28);
                solicitudRepository.save(solicitud28);
                tramite28.setSolicitud(solicitud28);
                tramiteRepository.save(tramite28);
                Solicitud solicitud29 = new Solicitud(null, fechaFebrero2024, solicitante1, tramite29);
                solicitudRepository.save(solicitud29);
                tramite29.setSolicitud(solicitud29);
                tramiteRepository.save(tramite29);
                Solicitud solicitud30 = new Solicitud(null, fechaMarzo2024, solicitante2, tramite30);
                solicitudRepository.save(solicitud30);
                tramite30.setSolicitud(solicitud30);
                tramiteRepository.save(tramite30);
                Solicitud solicitud31 = new Solicitud(null, fechaMayo2024, solicitante3, tramite31);
                solicitudRepository.save(solicitud31);
                tramite31.setSolicitud(solicitud31);
                tramiteRepository.save(tramite31);
        }
}