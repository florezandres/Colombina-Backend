package com.example.colombina.servicio;

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
}
