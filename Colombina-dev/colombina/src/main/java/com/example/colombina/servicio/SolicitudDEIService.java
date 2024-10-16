package com.example.colombina.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.colombina.entidad.SolicitudDEI;
import com.example.colombina.repositorio.SolicitudDEIRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudDEIService {

    @Autowired
    private SolicitudDEIRepository solicitudDEIRepository;

    // Crear una nueva solicitud DEI
    public SolicitudDEI crearSolicitudDEI(SolicitudDEI solicitudDEI) {
        return solicitudDEIRepository.save(solicitudDEI);
    }

    // Obtener todas las solicitudes DEI
    public List<SolicitudDEI> obtenerTodasLasSolicitudesDEI() {
        return solicitudDEIRepository.findAll();
    }

    // Obtener una solicitud DEI por ID
    public Optional<SolicitudDEI> obtenerSolicitudDEIPorId(Long id) {
        return solicitudDEIRepository.findById(id);
    }

    // Actualizar una solicitud DEI
    public SolicitudDEI actualizarSolicitudDEI(Long id, SolicitudDEI solicitudActualizada) {
        solicitudActualizada.setIdSolicitud(id);
        return solicitudDEIRepository.save(solicitudActualizada);
    }

    // Eliminar una solicitud DEI
    public void eliminarSolicitudDEI(Long id) {
        solicitudDEIRepository.deleteById(id);
    }

    // Buscar solicitudes por el nombre del solicitante
    public List<SolicitudDEI> buscarPorNombreSolicitante(String nombreSolicitante) {
        return solicitudDEIRepository.findByNombreSolicitante(nombreSolicitante);
    }

    // Buscar solicitudes por tipo de producto
    public List<SolicitudDEI> buscarPorTipoDeProducto(String tipoDeProducto) {
        return solicitudDEIRepository.findByTipoDeProducto(tipoDeProducto);
    }

    // Buscar solicitudes por fecha de solicitud
    public List<SolicitudDEI> buscarPorFechaSolicitud(Date fechaSolicitud) {
        return solicitudDEIRepository.findByFechaSolicitud(fechaSolicitud);
    }
}

