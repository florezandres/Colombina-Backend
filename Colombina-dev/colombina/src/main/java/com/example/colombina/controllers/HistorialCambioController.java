package com.example.colombina.controllers;
import com.example.colombina.DTOs.HistorialCambioDTO;
import com.example.colombina.services.HistorialCambioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
public class HistorialCambioController {
    @Autowired
    private HistorialCambioService historialCambioService;

    @GetMapping("/{tramiteId}")
    public ResponseEntity<List<HistorialCambioDTO>> obtenerHistorial(@PathVariable Long tramiteId) {
        List<HistorialCambioDTO> historial = historialCambioService.obtenerHistorialPorTramiteId(tramiteId);
        return ResponseEntity.ok(historial);
    }

    @PostMapping("/registrar")
    public ResponseEntity<HistorialCambioDTO> registrarCambio(@RequestParam Long tramiteId, @RequestParam String descripcion) {
        HistorialCambioDTO nuevoCambio = historialCambioService.registrarCambio(tramiteId, descripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCambio);
    }
}
