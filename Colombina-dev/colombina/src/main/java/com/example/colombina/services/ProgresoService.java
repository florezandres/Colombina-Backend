package com.example.colombina.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.colombina.model.Tramite;
import com.example.colombina.repositories.TramiteRepository;

@Service
public class ProgresoService {

    @Autowired
    private TramiteRepository tramiteRepository;

    // Actualiza el progreso del trámite
    public void actualizarProgreso(Long idTramite, double porcentajeIncremento) {
        Tramite tramite = tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("Trámite no encontrado"));

        // Actualizar el progreso del trámite
        tramite.setProgreso(tramite.getProgreso() + porcentajeIncremento);

        // Asegurarnos de que no pase del 100%
        if (tramite.getProgreso() > 100) {
            tramite.setProgreso(100);
        }

        // Guardar el trámite con el nuevo progreso
        tramiteRepository.save(tramite);
    }
}
