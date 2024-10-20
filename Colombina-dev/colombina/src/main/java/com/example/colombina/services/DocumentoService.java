package com.example.colombina.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.colombina.model.Documento;
import com.example.colombina.repositories.DocumentoRepository;


@Service
public class DocumentoService {

        @Autowired
        private DocumentoRepository documentoRepository;

    public boolean verificarDocumentosCompletos(Long tramiteId) {
        List<Documento> documentos = documentoRepository.findByTramiteId(tramiteId);
        // Verifica si todos los documentos están aprobados
        return documentos.stream().allMatch(Documento::isAprobado);
    }
}
