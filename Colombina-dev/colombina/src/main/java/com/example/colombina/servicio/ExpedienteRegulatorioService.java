package com.example.colombina.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.colombina.entidad.ExpedienteRegulatorio;
import com.example.colombina.repositorio.ExpedienteRegulatorioRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpedienteRegulatorioService {

    @Autowired
    private ExpedienteRegulatorioRepository expedienteRegulatorioRepository;

    // Crear un nuevo expediente regulatorio
    public ExpedienteRegulatorio crearExpedienteRegulatorio(ExpedienteRegulatorio expedienteRegulatorio) {
        return expedienteRegulatorioRepository.save(expedienteRegulatorio);
    }

    // Obtener todos los expedientes regulatorios
    public List<ExpedienteRegulatorio> obtenerTodosLosExpedientesRegulatorios() {
        return expedienteRegulatorioRepository.findAll();
    }

    // Obtener un expediente regulatorio por ID
    public Optional<ExpedienteRegulatorio> obtenerExpedienteRegulatorioPorId(Long id) {
        return expedienteRegulatorioRepository.findById(id);
    }

    // Actualizar un expediente regulatorio
    public ExpedienteRegulatorio actualizarExpedienteRegulatorio(Long id, ExpedienteRegulatorio expedienteActualizado) {
        expedienteActualizado.setIdExpediente(id);
        return expedienteRegulatorioRepository.save(expedienteActualizado);
    }

    // Eliminar un expediente regulatorio
    public void eliminarExpedienteRegulatorio(Long id) {
        expedienteRegulatorioRepository.deleteById(id);
    }

    // Buscar expedientes por número de expediente
    public List<ExpedienteRegulatorio> buscarPorNumeroExpediente(int numeroExpediente) {
        return expedienteRegulatorioRepository.findByNumeroExpediente(numeroExpediente);
    }

    // Buscar expedientes por estado
    public List<ExpedienteRegulatorio> buscarPorEstadoExpediente(boolean estadoExpediente) {
        return expedienteRegulatorioRepository.findByEstadoExpediente(estadoExpediente);
    }

    // Buscar expedientes por fecha de creación
    public List<ExpedienteRegulatorio> buscarPorFechaCreacion(Date fechaCreacion) {
        return expedienteRegulatorioRepository.findByFechaCreacion(fechaCreacion);
    }

    // Buscar expedientes por descripción
    public List<ExpedienteRegulatorio> buscarPorDescripcion(String keyword) {
        return expedienteRegulatorioRepository.findByDescripcionExpedienteContaining(keyword);
    }
}

