package com.example.colombina.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.colombina.entidad.DocumentoTecnico;
import com.example.colombina.repositorio.DocumentoTecnicoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentoTecnicoService {

    @Autowired
    private DocumentoTecnicoRepository documentoTecnicoRepository;

    // Crear un nuevo documento técnico
    public DocumentoTecnico crearDocumentoTecnico(DocumentoTecnico documentoTecnico) {
        return documentoTecnicoRepository.save(documentoTecnico);
    }

    // Obtener todos los documentos técnicos
    public List<DocumentoTecnico> obtenerTodosLosDocumentosTecnicos() {
        return documentoTecnicoRepository.findAll();
    }

    // Obtener un documento técnico por ID
    public Optional<DocumentoTecnico> obtenerDocumentoTecnicoPorId(Long id) {
        return documentoTecnicoRepository.findById(id);
    }

    // Actualizar un documento técnico
    public DocumentoTecnico actualizarDocumentoTecnico(Long id, DocumentoTecnico documentoTecnicoActualizado) {
        documentoTecnicoActualizado.setIdDocumento(id);
        return documentoTecnicoRepository.save(documentoTecnicoActualizado);
    }

    // Eliminar un documento técnico
    public void eliminarDocumentoTecnico(Long id) {
        documentoTecnicoRepository.deleteById(id);
    }

    // Buscar documentos por tipo de documento
    public List<DocumentoTecnico> buscarPorTipoDocumento(String tipoDocumento) {
        return documentoTecnicoRepository.findByTipoDocumento(tipoDocumento);
    }

    // Buscar documentos por fecha de adjunción
    public List<DocumentoTecnico> buscarPorFechaAdjuncion(Date fechaAdjuncion) {
        return documentoTecnicoRepository.findByFechaAdjuncion(fechaAdjuncion);
    }

    // Buscar documentos por estado
    public List<DocumentoTecnico> buscarPorEstadoDocumento(boolean estadoDocumento) {
        return documentoTecnicoRepository.findByEstadoDocumento(estadoDocumento);
    }
}
