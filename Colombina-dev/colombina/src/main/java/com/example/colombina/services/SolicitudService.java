package com.example.colombina.services;

import com.example.colombina.DTOs.SolicitudDTO;
import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.EntidadSanitaria;
import com.example.colombina.model.Solicitud;
import com.example.colombina.model.Tramite;
import com.example.colombina.model.Usuario;
import com.example.colombina.repositories.EntidadSanitariaRepository;
import com.example.colombina.repositories.SolicitudRepository;
import com.example.colombina.repositories.TramiteRepository;
import com.example.colombina.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private TramiteRepository tramiteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EntidadSanitariaRepository entidadSanitariaRepository;

    @Autowired
    private ModelMapper modelMapper; // Usaremos ModelMapper para la conversión a DTO

    public SolicitudDTO crearSolicitud(SolicitudDTO solicitudDTO, TramiteDTO tramiteDTO, Long idUsuario, Long idEntidad) {
        System.out.println("Entra crear servicio");
        // Verificar si ya existe un trámite con el mismo número radicado
        Optional<Tramite> tramiteExistente = tramiteRepository.findByNumeroRadicado(tramiteDTO.getNumeroRadicado());

        if (tramiteExistente.isPresent()) {
            System.out.println("Excepcion ya existe ese tramite");
            throw new IllegalArgumentException("Ya existe un trámite con el número radicado: " + tramiteDTO.getNumeroRadicado());
        }

        // Obtener el usuario solicitante
        Usuario solicitante = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el ID: " + idUsuario));
        System.out.println("Usuario encontrado "+solicitante.getId());

        // Obtener el usuario solicitante
        EntidadSanitaria entidadSanitaria = entidadSanitariaRepository.findById(idEntidad)
                .orElseThrow(() -> new IllegalArgumentException("Entidad sanitaria no encontrado con el ID: " + idUsuario));
        System.out.println("Entidad sanitaria encontrada "+solicitante.getId());

        // Crear y guardar el nuevo Tramite
        Tramite nuevoTramite = modelMapper.map(tramiteDTO, Tramite.class); // Convertir el DTO a entidad
        nuevoTramite.setEstado(Tramite.EstadoTramite.PENDIENTE); // Asignar estado pendiente
        nuevoTramite.setNumeroRadicado(); // Generar número de radicado
        nuevoTramite.setFechaRadicacion(new Date()); // Asignar la fecha actual
        nuevoTramite.setEntidadSanitaria(entidadSanitaria); // Asociar la entidad sanitaria
        System.out.println("Antes de guardar tramite: "+nuevoTramite.getNumeroRadicado());
        Tramite tramiteGuardado = tramiteRepository.save(nuevoTramite);
        System.out.println("Tramite guardado: "+tramiteGuardado.getNumeroRadicado());
        // Crear y guardar la nueva Solicitud
        Solicitud nuevaSolicitud = modelMapper.map(solicitudDTO, Solicitud.class); // Convertir el DTO a entidad
        nuevaSolicitud.setSolicitante(solicitante); // Asociar el usuario solicitante

        Solicitud solicitudGuardada = solicitudRepository.save(nuevaSolicitud);

        System.out.println("Antes de return");
        // Convertir la solicitud guardada a DTO y devolverla
        return modelMapper.map(solicitudGuardada, SolicitudDTO.class);
    }
}

