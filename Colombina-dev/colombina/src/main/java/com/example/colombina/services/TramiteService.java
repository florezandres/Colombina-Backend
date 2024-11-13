package com.example.colombina.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.colombina.DTOs.ComentarioDTO;
import com.example.colombina.DTOs.InfoAperturaTramiteDTO;
import com.example.colombina.DTOs.InfoControlTramiteDTO;
import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.Comentario;
import com.example.colombina.model.HistorialCambio;
import com.example.colombina.model.Seguimiento;
import com.example.colombina.model.Tramite;
import com.example.colombina.model.Usuario;
import com.example.colombina.repositories.HistorialCambioRepository;
import com.example.colombina.repositories.SeguimientoRepository;
import com.example.colombina.repositories.TramiteRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TramiteService {

    @Autowired
    private TramiteRepository tramiteRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SeguimientoRepository seguimientoRepository;    

    @Autowired
    private HistorialCambioRepository historialCambioRepository;

    @Autowired
    private NotificacionService notificacionService;

    // Cambia el estado de un trámite a EN_REVISION
    public void abrirTramite(Long idTramite, InfoAperturaTramiteDTO infoTramite) {
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));
        tramite.setPt(infoTramite.getPt());
        tramite.setUnidadNegocio(infoTramite.getUnidadNegocio());
        tramite.setNumProyectoSap(infoTramite.getNumProyectoSap());
        tramite.setProyecto(infoTramite.getProyecto());
        tramite.setTipoModificacion(infoTramite.getTipoModificacion());
        tramite.setEtapa(4);
        tramite.setProgreso();
        tramiteRepository.save(tramite);

        Long solicitudId =tramite.getId()-1;
        notificacionService.enviarNotificacionAperturaTramite(solicitudId);
        log.info("Trámite abierto correctamente.");
    }
    public void updateStatus(Long idTramite, String status, String rejectionReason) {
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));
        
        tramite.setEstado(Tramite.EstadoTramite.valueOf(status));  // Actualizar el estado
        if ("REJECTED".equals(status)) { // Si es rechazado, añadir razón de rechazo
            tramite.setRejectionReason(rejectionReason); // Asegúrate de que este método exista en la clase Tramite
        }
        
        tramiteRepository.save(tramite); // Guardar los cambios en la base de datos
        log.info("Estado del trámite actualizado correctamente.");
    }
    

    public void documentacionRevisada(Long idTramite) {
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));
        tramite.setEtapa(5);
        tramite.setProgreso();
        tramiteRepository.save(tramite);
        log.info("Documentación revisada correctamente.");
    }

    public void infoControl(Long idTramite, InfoControlTramiteDTO infoTramite) {
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));

        tramite.setFechaTerminacion(infoTramite.getFechaTerminacion());
        tramite.setFechaNotificacion(infoTramite.getFechaNotificacion());
        tramite.setIdSeguimiento(infoTramite.getIdSeguimiento());
        tramite.setRegistroSanitario(infoTramite.getRegistroSanitario());
        tramite.setExpedienteRSA(infoTramite.getExpedienteRSA());
        tramite.setNumeroRSA(infoTramite.getNumeroRSA());
        tramite.setFechaVencimientoRSA(infoTramite.getFechaVencimientoRSA());
        tramite.setPlanta(infoTramite.getPlanta());
        tramite.setNumeroFactura(infoTramite.getNumeroFactura());
        tramite.setObservaciones(infoTramite.getObservaciones());
        tramite.setEtapa(6);
        tramite.setProgreso();
        tramiteRepository.save(tramite);
        log.info("Trámite abierto correctamente.");
    }
    
    //HU-43 - Elimina un tramite que este incompleto
    //Rol que utiliza el metodo: ASUNTOSREG (Agente de la Agencia de Asuntos Regulatorios)
    public void eliminarTramite(Long idTramite) {
        // Buscar el trámite por su ID
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));
        
        // Verificar que el trámite no esté en proceso (EN_REVISION o APROBADO)
        if (tramite.getEstado() == Tramite.EstadoTramite.EN_REVISION || tramite.getEstado() == Tramite.EstadoTramite.APROBADO) {
            throw new IllegalArgumentException("El trámite está en proceso y no puede ser eliminado.");
        }

        // Elimina el trámite
        tramiteRepository.delete(tramite);
    }

    /*//Traer todos los tramites
    public List<Tramite> findAll() {
        return tramiteRepository.findAll();
    }*/

    public List<TramiteDTO> findAll() {
        try {
            List<Tramite> tramites = tramiteRepository.findAll();
            
            // Mapear entidades a DTOs
            Type listType = new TypeToken<List<TramiteDTO>>() {}.getType();
            return modelMapper.map(tramites, listType);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar trámites", e);
        }
    }

    //Find by id
    public TramiteDTO findById(Long id) {
        Tramite tramite = tramiteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + id + " no existe."));
        TramiteDTO tramiteDTO = modelMapper.map(tramite, TramiteDTO.class);
        System.out.println("Entidad sanitaria: " + tramite.getEntidadSanitaria().getId());
        tramiteDTO.setEntidadSanitariaId(tramite.getEntidadSanitaria().getId());
        return tramiteDTO;
    }

    public Tramite findByIdEntity(Long id) {
        return tramiteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + id + " no existe."));
    }


    //HU-39 - Filtrar tramites por estado
    public List<TramiteDTO> filtrarTramitesPorEstado(Tramite.EstadoTramite estado) {
        //return tramiteRepository.findByEstado(estado);
        return null;
    }

    //HU-13
    // Método para actualizar el seguimiento y calcular el tiempo de revisión
    public void actualizarSeguimiento(Long idTramite, String nuevoEstado) {
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));

        // Crear nuevo seguimiento
        Seguimiento seguimiento = new Seguimiento();
        seguimiento.setTramite(tramite);
        seguimiento.setEstadoActual(nuevoEstado);
        seguimiento.setFechaSeguimiento(new Date());
        seguimiento.setFechaFinSeguimiento(new Date()); 

        // Calcular el tiempo de revisión
        long tiempo = seguimiento.getFechaFinSeguimiento().getTime() - seguimiento.getFechaInicioSeguimiento().getTime();
        seguimiento.setTiempoRevision(tiempo);

        // Guardar seguimiento
        seguimientoRepository.save(seguimiento);
    }

   // HU-35 - Modificar un tramite
public void modificarTramite(Long idTramite, String nuevoEstado) {
    Tramite tramite = tramiteRepository.findById(idTramite)
            .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));
    // Verificar si el nuevo estado es válido
    try {
        Tramite.EstadoTramite estado = Tramite.EstadoTramite.valueOf(nuevoEstado);
        tramite.setEstado(estado);
        tramiteRepository.save(tramite);
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Estado no válido.");
    }
}

    // HU-53 - Generar reportes personalizados
    public List<TramiteDTO> generarReportePersonalizado(String estado, Date fechaInicio, Date fechaFin) {
        // Filtrar por estado
        List<Tramite> tramites;
        if (estado != null) {
            Tramite.EstadoTramite estadoTramite = Tramite.EstadoTramite.valueOf(estado);
            tramites = tramiteRepository.findByEstado(estadoTramite);
        } else {
            tramites = tramiteRepository.findAll();
        }

        // Filtrar por fechas
        List<TramiteDTO> reporte = new ArrayList<>();
        for (Tramite tramite : tramites) {
            if ((fechaInicio == null || !tramite.getFechaRadicacion().before(fechaInicio)) &&
                    (fechaFin == null || !tramite.getFechaRadicacion().after(fechaFin))) {
                TramiteDTO dto = new TramiteDTO();
                dto.setId(tramite.getId());
                dto.setEstado(tramite.getEstado());
                dto.setFechaRadicacion(tramite.getFechaRadicacion());
                reporte.add(dto);
            }
        }
        return reporte;
    }

    public void agregarComentarioAlHistorial(Long idTramite, ComentarioDTO comentarioDTO, String descripcionCambio) {
        // Buscar el trámite por su ID
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));

        // Crear una nueva entrada de historial de cambios
        HistorialCambio historialCambio = new HistorialCambio();
        historialCambio.setTramite(tramite);
        historialCambio.setDescripcion(descripcionCambio);
        historialCambio.setFechaCambio(new Date());// Fecha actual


        // Crear un nuevo comentario basado en el DTO
        Comentario comentario = new Comentario();
        comentario.setUsuarioDestino(new Usuario(comentarioDTO.getIdUsuarioDestino()));  // Asignar los usuarios
        comentario.setUsuarioOrigen(new Usuario(comentarioDTO.getIdUsuarioOrigen()));
        comentario.setComentario(comentarioDTO.getComentario());
        comentario.setHistorialCambio(historialCambio);  // Asociar el comentario con el historial

        // Añadir el comentario al historial de cambios
        historialCambio.getComentarios().add(comentario);
        // Guardar la entrada en el repositorio del historial de cambios
        historialCambioRepository.save(historialCambio);
    }

    public void asociarNumeroRadicadoYLLave(Long idTramite, String numeroRadicado, Double llave) {
        // Buscar el trámite por su ID
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));

        // Asociar el número de radicado y la llave
        tramite.setExpedienteRSA(numeroRadicado);
        tramite.setLlave(llave);

        // Guardar los cambios en la base de datos
        tramiteRepository.save(tramite);

        Long idSolicitud = tramite.getId()-1;
        notificacionService.enviarNotificacionTramiteAceptadoInvima(idSolicitud);
    }

    public void agregarInfoControl(Long idTramite, TramiteDTO tramiteDTO) {
        // Buscar el trámite por su ID
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));

        // Actualizar los campos de información de control
        tramite.setPt(tramiteDTO.getPt());
        tramite.setUnidadNegocio(tramiteDTO.getUnidadNegocio());
        tramite.setNumProyectoSap(tramiteDTO.getNumProyectoSap());
        tramite.setProyecto(tramiteDTO.getProyecto());
        tramite.setTipoModificacion(tramiteDTO.getTipoModificacion());

        // Guardar los cambios en la base de datos
        tramiteRepository.save(tramite);
    }

    public Optional<Tramite> obtenerTramitePorId(Long id) {
        return tramiteRepository.findById(id);
    }
    
    public Tramite actualizarTramite(Long id, Tramite detallesTramite) {
    
        // Guardar el trámite actualizado en el repositorio
        return tramiteRepository.save(detallesTramite);
    }
    
}
