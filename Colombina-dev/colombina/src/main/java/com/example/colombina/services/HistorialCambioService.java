package com.example.colombina.services;

import org.springframework.stereotype.Service;
import com.example.colombina.DTOs.HistorialCambioDTO;
import com.example.colombina.model.HistorialCambio;
import com.example.colombina.model.Tramite;
import com.example.colombina.repositories.HistorialCambioRepository;
import com.example.colombina.repositories.TramiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialCambioService {
    @Autowired
    private HistorialCambioRepository historialCambioRepository;

    @Autowired
    private TramiteRepository tramiteRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<HistorialCambioDTO> obtenerHistorialPorTramiteId(Long tramiteId) {
        List<HistorialCambio> historial = historialCambioRepository.findByTramiteId(tramiteId);
        return historial.stream()
                .map(cambio -> modelMapper.map(cambio, HistorialCambioDTO.class))
                .collect(Collectors.toList());
    }
    public HistorialCambioDTO registrarCambio(Long tramiteId, String descripcion) {
        Tramite tramite = tramiteRepository.findById(tramiteId).orElseThrow(() -> new RuntimeException("Trámite no encontrado"));
        HistorialCambio cambio = new HistorialCambio();
        cambio.setTramite(tramite);
        cambio.setDescripcion(descripcion);
        cambio.setFechaCambio(new Date());
        HistorialCambio nuevoCambio = historialCambioRepository.save(cambio);
        return modelMapper.map(nuevoCambio, HistorialCambioDTO.class);
    }

}
