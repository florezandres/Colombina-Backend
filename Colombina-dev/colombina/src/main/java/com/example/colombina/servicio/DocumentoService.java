package com.example.colombina.servicio;

import com.example.colombina.entidad.Documento;
import com.example.colombina.entidad.Tramite;
import com.example.colombina.repositorio.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class DocumentoService {
    private final DocumentoRepository documentoRepository;

    @Autowired
    public DocumentoService(DocumentoRepository documentoRepository) {
        this.documentoRepository = documentoRepository;
    }

    public Documento saveDocumento(Documento documento) {
        return documentoRepository.save(documento);
    }

    public Optional<Documento> getDocumentoById(Long id) {
        return documentoRepository.findById(id);
    }


    public List<Documento> getDocumentosByTramite(Tramite tramite) {
        return documentoRepository.findByTramite(tramite);
    }


    public List<Documento> getDocumentosByInformacionContaining(String informacion) {
        return documentoRepository.findByInformacionContaining(informacion);
    }


    public void deleteDocumentoById(Long id) {
        documentoRepository.deleteById(id);
    }

    public List<Documento> getAllDocumentos() {
        return documentoRepository.findAll();
    }
}