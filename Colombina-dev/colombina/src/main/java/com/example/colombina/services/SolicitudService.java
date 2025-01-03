package com.example.colombina.services;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.colombina.DTOs.SolicitudDTO;
import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.EntidadSanitaria;
import com.example.colombina.model.Solicitud;
import com.example.colombina.model.Tramite;
import com.example.colombina.model.Usuario;
import com.example.colombina.repositories.SolicitudRepository;
import com.example.colombina.repositories.TramiteRepository;
import com.example.colombina.repositories.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private TramiteRepository tramiteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private ModelMapper modelMapper; // Usaremos ModelMapper para la conversión a DTO

    public SolicitudDTO crearSolicitud(SolicitudDTO solicitudDTO, TramiteDTO tramiteDTO, String username) {
        log.info("Entra crear servicio");

        // Obtener el usuario solicitante
        Usuario solicitante = usuarioRepository.findByNombre(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el nombre: " + username));
        log.info("Usuario encontrado " + solicitante.getId());

        Long nextId = solicitudRepository.count();

        
        // Crear y guardar el nuevo Tramite
        tramiteDTO.setEtapa("2");
        Tramite nuevoTramite = modelMapper.map(tramiteDTO, Tramite.class); // Convertir el DTO a entidad
        nuevoTramite.setEstado(Tramite.EstadoTramite.PENDIENTE); // Asignar estado pendiente
        nuevoTramite.setNumeroRadicado("AR-" + (nextId + 50)); // Generar número de radicado
        nuevoTramite.setEtapa(2);
        nuevoTramite.setProgreso();
        nuevoTramite.setEntidadSanitaria(new EntidadSanitaria(tramiteDTO.getEntidadSanitariaId()));
        Tramite tramiteGuardado = tramiteRepository.save(nuevoTramite);
        log.info("Tramite guardado: " + tramiteGuardado.getNumeroRadicado());
        // Crear y guardar la nueva Solicitud
        Solicitud nuevaSolicitud = modelMapper.map(solicitudDTO, Solicitud.class); // Convertir el DTO a entidad
        nuevaSolicitud.setSolicitante(solicitante); // Asociar el usuario solicitante
        nuevaSolicitud.setTramite(tramiteGuardado); // Asociar el trámite creado

        Solicitud solicitudGuardada = solicitudRepository.save(nuevaSolicitud);
        log.info("Solicitud guardada: " + solicitudGuardada.getId());
        notificacionService.enviarNotificacionNuevaSolicitud(solicitudGuardada.getId());
        // Convertir la solicitud guardada a DTO y devolverla
        return modelMapper.map(solicitudGuardada, SolicitudDTO.class);
    }

    public SolicitudDTO getSolicitudByTramite(Long id) {
        Solicitud solicitud = solicitudRepository.findByTramiteId(id);
        return modelMapper.map(solicitud, SolicitudDTO.class);
    }

    public List<Solicitud> getSolicitudesPorSolicitante(String username, Integer page, Integer limit) {
        Usuario solicitante = usuarioRepository.findByNombre(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el nombre: " + username));
        Pageable pageable = PageRequest.of(page - 1, limit);
        return solicitudRepository.findBySolicitanteWithTramite(solicitante, pageable);
    }

    public List<Solicitud> findByFiltersAndSolicitante(String username, Integer page, Integer limit, Tramite.EstadoTramite estado, String tipoTramite,
            Tramite.TipoTramite nacionalidad, Date fechaInicio, Date fechaFin, String filtro) {
        Usuario solicitante = usuarioRepository.findByNombre(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el nombre: " + username));
        filtro = filtro == null ? "" : filtro;
        Pageable pageable = PageRequest.of(page - 1, limit);
        return solicitudRepository.findByFiltersAndSolicitante(solicitante, estado, tipoTramite, nacionalidad, fechaInicio, fechaFin, filtro, pageable);
    }

    public List<Solicitud> getSolicitudes(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return solicitudRepository.findAllByOrderByIdDesc(pageable);
    }

    public List<Solicitud> findByFilters(Integer page, Integer limit, Tramite.EstadoTramite estado, String tipoTramite, Tramite.TipoTramite nacionalidad, Date fechaInicio,
    Date fechaFin, String filtro) {
        filtro = filtro == null ? "" : filtro;
        Pageable pageable = PageRequest.of(page - 1, limit);
        return solicitudRepository.findByFilters(estado, tipoTramite, nacionalidad, fechaInicio, fechaFin, filtro, pageable);
    }
}
