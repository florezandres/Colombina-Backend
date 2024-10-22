package com.example.colombina.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.colombina.DTOs.ComentarioDTO;
import com.example.colombina.DTOs.HistorialCambioDTO;
import com.example.colombina.model.*;
import com.example.colombina.repositories.HistorialCambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.repositories.TramiteRepository;

@Service
public class TramiteService {

    @Autowired
    private TramiteRepository tramiteRepository;


    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private HistorialCambioRepository historialCambioRepository;

    // Cambia el estado de un trámite a EN_REVISION
    public void abrirTramite(Long idTramite) {
        // 1. Buscar el trámite por su ID
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));

        // 2. Verificar si el estado actual es PENDIENTE
        if (tramite.getEstado() != Tramite.EstadoTramite.PENDIENTE) {
            throw new IllegalArgumentException("El trámite no está en estado PENDIENTE, no puede ser abierto.");
        }

        // 3. Cambiar el estado del trámite a EN_REVISION
        tramite.setEstado(Tramite.EstadoTramite.EN_REVISION);

        // 4. Guardar los cambios en la base de datos
        tramiteRepository.save(tramite);
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

    //HU-39 - Filtrar tramites por estado
    public List<TramiteDTO> filtrarTramitesPorEstado(Tramite.EstadoTramite estado) {
        //return tramiteRepository.findByEstado(estado);
        return null;
    }

    public String generarResumenDocumentos(Long tramiteId) {
        Tramite tramite = tramiteRepository.findById(tramiteId)
                .orElseThrow(() -> new IllegalArgumentException("Trámite no encontrado"));

        List<Documento> documentos = tramite.getDocumentos();
        StringBuilder resumen = new StringBuilder();

        // Genera un resumen de los documentos y su estado
        documentos.forEach(documento -> {
            resumen.append("Documento: ")
                    .append(documento.getTipo())
                    .append(" - Aprobado: ")
                    .append(documento.isAprobado() ? "Sí" : "No")
                    .append("\n");
        });

        return resumen.toString();
    }

    public void consolidarTramite(Long tramiteId) {
        //  Verifica que todos los documentos estén completos
        if (!documentoService.verificarDocumentosCompletos(tramiteId)) {
            throw new IllegalArgumentException("No todos los documentos están aprobados.");
        }

        // Genera el resumen
        String resumen = generarResumenDocumentos(tramiteId);

        // Envía la notificación
        notificacionService.enviarNotificacion(tramiteId, "Consolidación completada", resumen);

        // Puedes hacer más operaciones aquí si lo necesitas
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
        tramite.setNumeroRadicado(numeroRadicado);
        tramite.setLlave(llave);

        // Guardar los cambios en la base de datos
        tramiteRepository.save(tramite);
    }

}