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


    public List<Documento> getDocumentsByTramiteId(Long tramiteId) {
        return documentoRepository.findByTramiteId(tramiteId);
    }
}
