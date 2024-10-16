package com.example.colombina.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.colombina.entidad.SeguimientoTramite;
import com.example.colombina.repositorio.SeguimientoTramiteRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SeguimientoTramiteService {

    @Autowired
    private SeguimientoTramiteRepository seguimientoTramiteRepository;

    // Crear un nuevo seguimiento de trámite
    public SeguimientoTramite crearSeguimientoTramite(SeguimientoTramite seguimientoTramite) {
        return seguimientoTramiteRepository.save(seguimientoTramite);
    }

    // Obtener todos los seguimientos de trámite
    public List<SeguimientoTramite> obtenerTodosLosSeguimientos() {
        return seguimientoTramiteRepository.findAll();
    }

    // Obtener un seguimiento de trámite por ID
    public Optional<SeguimientoTramite> obtenerSeguimientoTramitePorId(Long id) {
        return seguimientoTramiteRepository.findById(id);
    }

    // Actualizar un seguimiento de trámite
    public SeguimientoTramite actualizarSeguimientoTramite(Long id, SeguimientoTramite seguimientoActualizado) {
        seguimientoActualizado.setIdSeguimiento(id);
        return seguimientoTramiteRepository.save(seguimientoActualizado);
    }

    // Eliminar un seguimiento de trámite
    public void eliminarSeguimientoTramite(Long id) {
        seguimientoTramiteRepository.deleteById(id);
    }

    // Buscar seguimientos por el estado actual
    public List<SeguimientoTramite> buscarPorEstadoActual(String estadoActual) {
        return seguimientoTramiteRepository.findByEstadoActual(estadoActual);
    }

    // Buscar seguimientos por fecha de seguimiento
    public List<SeguimientoTramite> buscarPorFechaSeguimiento(Date fechaSeguimiento) {
        return seguimientoTramiteRepository.findByFechaSeguimiento(fechaSeguimiento);
    }

    // Buscar seguimientos por rango de fechas
    public List<SeguimientoTramite> buscarPorRangoDeFechas(Date fechaInicio, Date fechaFin) {
        return seguimientoTramiteRepository.findByFechaSeguimientoBetween(fechaInicio, fechaFin);
    }
}

