package com.example.colombina.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.colombina.entidad.EntidadSanitariaINVIMA;
import com.example.colombina.repositorio.EntidadSanitariaINVIMARepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EntidadSanitariaINVIMAService {

    @Autowired
    private EntidadSanitariaINVIMARepository entidadSanitariaINVIMARepository;

    // Crear una nueva entidad sanitaria INVIMA
    public EntidadSanitariaINVIMA crearEntidadSanitariaINVIMA(EntidadSanitariaINVIMA entidadSanitariaINVIMA) {
        return entidadSanitariaINVIMARepository.save(entidadSanitariaINVIMA);
    }

    // Obtener todas las entidades sanitarias INVIMA
    public List<EntidadSanitariaINVIMA> obtenerTodasLasEntidadesSanitariasINVIMA() {
        return entidadSanitariaINVIMARepository.findAll();
    }

    // Obtener una entidad sanitaria INVIMA por ID
    public Optional<EntidadSanitariaINVIMA> obtenerEntidadSanitariaINVIMAPorId(Long id) {
        return entidadSanitariaINVIMARepository.findById(id);
    }

    // Actualizar una entidad sanitaria INVIMA
    public EntidadSanitariaINVIMA actualizarEntidadSanitariaINVIMA(Long id, EntidadSanitariaINVIMA entidadSanitariaActualizada) {
        entidadSanitariaActualizada.setIdEntidad(id);
        return entidadSanitariaINVIMARepository.save(entidadSanitariaActualizada);
    }

    // Eliminar una entidad sanitaria INVIMA
    public void eliminarEntidadSanitariaINVIMA(Long id) {
        entidadSanitariaINVIMARepository.deleteById(id);
    }

    // Buscar por resultado del trámite
    public List<EntidadSanitariaINVIMA> buscarPorResultadoTramite(boolean resultadoTramite) {
        return entidadSanitariaINVIMARepository.findByResultadoTramite(resultadoTramite);
    }

    // Buscar por fecha de radicación
    public List<EntidadSanitariaINVIMA> buscarPorFechaRadicacion(Date fechaRadicacion) {
        return entidadSanitariaINVIMARepository.findByFechaRadicacion(fechaRadicacion);
    }

    // Buscar por comentarios que contengan una palabra clave
    public List<EntidadSanitariaINVIMA> buscarPorComentarios(String keyword) {
        return entidadSanitariaINVIMARepository.findByComentariosContaining(keyword);
    }
}

