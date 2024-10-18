package com.example.colombina.services;

import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.Tramite;
import com.example.colombina.repositories.TramiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TramiteService {

    @Autowired
    private TramiteRepository tramiteRepository;

    // Cambia el estado de un trámite a EN_REVISION
    public void abrirTramite(Long idTramite) {
        // 1. Buscar el trámite por su ID
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("El trámite con ID " + idTramite + " no existe."));

        // 2. Verificar si el estado actual es PENDIENTE
        if (tramite.getEstado() != Tramite.EstadoTramite.PENDIENTE) {
            throw new IllegalArgumentException("El trámite no está en estado PENDIENTE, no puede ser abierto.");
        }

        // 3. Cambiar el estado del trámite a EN_REVISION
        tramite.setEstado(Tramite.EstadoTramite.EN_REVISION);

        // 4. Guardar los cambios en la base de datos
        tramiteRepository.save(tramite);
    }
}
