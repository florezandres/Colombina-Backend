package com.example.colombina.servicio;

import com.example.colombina.entidad.SolicitudDEI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.colombina.entidad.AsuntoRegulatorio;
import com.example.colombina.repositorio.AsuntoRegulatorioRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AsuntoRegulatorioService {

    @Autowired
    private AsuntoRegulatorioRepository asuntoRegulatorioRepository;

    @Autowired
    private SolicitudDEIService solicitudDEIService;

    // Crear un nuevo asunto regulatorio
    public AsuntoRegulatorio crearAsuntoRegulatorio(AsuntoRegulatorio asuntoRegulatorio) {
        return asuntoRegulatorioRepository.save(asuntoRegulatorio);
    }

    // Obtener todos los asuntos regulatorios
    public List<AsuntoRegulatorio> obtenerTodosLosAsuntosRegulatorios() {
        return asuntoRegulatorioRepository.findAll();
    }

    // Obtener un asunto regulatorio por ID
    public Optional<AsuntoRegulatorio> obtenerAsuntoRegulatorioPorId(Long id) {
        return asuntoRegulatorioRepository.findById(id);
    }

    // Actualizar un asunto regulatorio
    public AsuntoRegulatorio actualizarAsuntoRegulatorio(Long id, AsuntoRegulatorio asuntoRegulatorioActualizado) {
        asuntoRegulatorioActualizado.setIdAsuntoRegulatorio(id);
        return asuntoRegulatorioRepository.save(asuntoRegulatorioActualizado);
    }

    // Eliminar un asunto regulatorio
    public void eliminarAsuntoRegulatorio(Long id) {
        asuntoRegulatorioRepository.deleteById(id);
    }

    // Buscar asuntos regulatorios por responsable
    public List<AsuntoRegulatorio> buscarPorResponsable(String responsable) {
        return asuntoRegulatorioRepository.findByResponsable(responsable);
    }

    // Buscar asuntos regulatorios por estado
    public List<AsuntoRegulatorio> buscarPorEstadoAsunto(boolean estadoAsunto) {
        return asuntoRegulatorioRepository.findByEstadoAsunto(estadoAsunto);
    }

    // Buscar asuntos regulatorios por fecha
    public List<AsuntoRegulatorio> buscarPorFechaAsunto(Date fechaAsunto) {
        return asuntoRegulatorioRepository.findByFechaAsunto(fechaAsunto);
    }

    //CU 1 Solicitud trámite nacional--------------------------------------------------
    public List<String> obtenerDocumentosDeSolicitudEnAsunto(Long asuntoId, Long solicitudId) {
        // Buscar el AsuntoRegulatorio por ID
        AsuntoRegulatorio asuntoRegulatorio = asuntoRegulatorioRepository.findById(asuntoId)
                .orElseThrow(() -> new IllegalArgumentException("Asunto Regulatorio no encontrado"));

        // Buscar la solicitud dentro del AsuntoRegulatorio
        SolicitudDEI solicitud = asuntoRegulatorio.getSolicitudesDEI().stream()
                .filter(s -> s.getIdSolicitud().equals(solicitudId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada en el asunto"));

        // Delegar al servicio de solicitudes para obtener los documentos de la solicitud
        return solicitudDEIService.obtenerDocumentosDeSolicitud(solicitudId);
    }
}
