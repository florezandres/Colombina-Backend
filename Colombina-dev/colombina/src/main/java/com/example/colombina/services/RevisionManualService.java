package com.example.colombina.services;

import com.example.colombina.DTOs.RevisionManualDTO;
import com.example.colombina.model.RevisionManual;
import com.example.colombina.model.Tramite;
import com.example.colombina.repositories.RevisionManualRepository;
import com.example.colombina.repositories.TramiteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RevisionManualService {

    @Autowired
    private RevisionManualRepository revisionManualRepository;

    @Autowired
    private TramiteRepository tramiteRepository;

    public RevisionManualDTO obtenerRevisionPorTramite(Long tramiteId) {
        Optional<RevisionManual> revision = revisionManualRepository.findByTramiteId(tramiteId);
        return revision.map(this::convertirADTO).orElse(null);
    }

    public RevisionManualDTO realizarRevision(Long tramiteId, String comentarios) {
        Optional<RevisionManual> revisionExistente = revisionManualRepository.findByTramiteId(tramiteId);
        RevisionManual revision;

        if (revisionExistente.isPresent()) {
            revision = revisionExistente.get();
            revision.setComentarios(comentarios);
            revision.setRevisionCompleta(true);
            revision.setFechaRevision(LocalDateTime.now());
        } else {
            revision = new RevisionManual();
            Tramite tramite = tramiteRepository.findById(tramiteId).get();
            // TODO: modificar el atributo correcto
            revision.setTramite(tramite); // Relación con el trámite
            revision.setComentarios(comentarios);
            revision.setRevisionCompleta(true);
            revision.setFechaRevision(LocalDateTime.now());
        }

        RevisionManual revisionGuardada = revisionManualRepository.save(revision);
        return convertirADTO(revisionGuardada);
    }

    private RevisionManualDTO convertirADTO(RevisionManual revision) {
        RevisionManualDTO dto = new RevisionManualDTO();
        dto.setId(revision.getId());
        dto.setComentarios(revision.getComentarios());
        dto.setRevisionCompleta(revision.isRevisionCompleta());
        dto.setFechaRevision(revision.getFechaRevision());
        return dto;
    }
}
