package com.example.colombina.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.colombina.DTOs.EstadisticasDTO;
import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.Documento;
import com.example.colombina.model.Notificacion;
import com.example.colombina.model.Seguimiento;
import com.example.colombina.model.Tramite;
import com.example.colombina.model.Usuario;
import com.example.colombina.repositories.SeguimientoRepository;
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
    private ModelMapper modelMapper;

    @Autowired
    private SeguimientoRepository seguimientoRepository;

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

    //Traer todos los tramites
    public List<TramiteDTO> findAll() {
        return Collections.singletonList(modelMapper.map(tramiteRepository.findAll(), TramiteDTO.class));
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
        notificacionService.guardarNotificacion(tramiteId, "Consolidación completada", resumen);

        // Puedes hacer más operaciones aquí si lo necesitas
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
                dto.setNumeroRadicado(tramite.getNumeroRadicado());
                dto.setEstado(tramite.getEstado());
                dto.setFechaRadicacion(tramite.getFechaRadicacion());
                reporte.add(dto);
            }
        }
        return reporte;
    }

      // Obtener trámites nacionales agrupados por mes
    public List<EstadisticasDTO> obtenerTramitesNacionalesPorMes() {
        return tramiteRepository.contarTramitesPorMesYTipo("nacional");
    }

    // Obtener trámites internacionales agrupados por mes
    public List<EstadisticasDTO> obtenerTramitesInternacionalesPorMes() {
        return tramiteRepository.contarTramitesPorMesYTipo("internacional");
    }

    // Obtener documentos devueltos agrupados por tipo
    public List<EstadisticasDTO> obtenerDocumentosDevueltosPorTipo() {
        return tramiteRepository.contarDocumentosDevueltosPorTipo();
    }
    //Filtra los tramites por periodos
    public List<EstadisticasDTO> obtenerTramitesPorPeriodo(String tipo, String periodo) {
        switch (periodo.toLowerCase()) {
            case "semanas":
                return tramiteRepository.contarTramitesPorSemanaYTipo(tipo);
            case "meses":
                return tramiteRepository.contarTramitesPorMesYTipo(tipo);
            case "años":
                return tramiteRepository.contarTramitesPorAnoYTipo(tipo);
            default:
                throw new IllegalArgumentException("Periodo no válido. Utiliza 'semanas', 'meses' o 'años'.");
        }
    }
  
    //Filtros para tramites
    public List<EstadisticasDTO> obtenerTramitesFiltrados(String tipo, String pais, String fechaInicio, String fechaFin) {
        return tramiteRepository.filtrarTramites(tipo, pais, fechaInicio, fechaFin);
    }

   
}
