package com.example.colombina.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.colombina.entidad.ArchivoControlDeTramites;
import com.example.colombina.repositorio.ArchivoControlDeTramitesRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArchivoControlDeTramitesService {

    @Autowired
    private ArchivoControlDeTramitesRepository archivoRepository;

    // Crear un nuevo archivo
    public ArchivoControlDeTramites crearArchivo(ArchivoControlDeTramites archivo) {
        return archivoRepository.save(archivo);
    }

    // Obtener todos los archivos
    public List<ArchivoControlDeTramites> obtenerTodosLosArchivos() {
        return archivoRepository.findAll();
    }

    // Obtener un archivo por ID
    public Optional<ArchivoControlDeTramites> obtenerArchivoPorId(Long id) {
        return archivoRepository.findById(id);
    }

    // Actualizar un archivo
    public ArchivoControlDeTramites actualizarArchivo(Long id, ArchivoControlDeTramites archivoActualizado) {
        archivoActualizado.setIdArchivoControlDeTramites(id);
        return archivoRepository.save(archivoActualizado);
    }

    // Eliminar un archivo
    public void eliminarArchivo(Long id) {
        archivoRepository.deleteById(id);
    }

    // Buscar archivos por estado del trámite
    public List<ArchivoControlDeTramites> buscarPorEstadoTramite(boolean estadoTramite) {
        return archivoRepository.findByEstadoTramite(estadoTramite);
    }

    // Buscar archivos por fecha de creación
    public List<ArchivoControlDeTramites> buscarPorFechaCreacion(Date fechaCreacion) {
        return archivoRepository.findByFechaCreacion(fechaCreacion);
    }

    // Buscar archivos por historial de modificaciones
    public List<ArchivoControlDeTramites> buscarPorHistorialDeModificaciones(String keyword) {
        return archivoRepository.findByHistorialDeModificacionesContaining(keyword);
    }
}
