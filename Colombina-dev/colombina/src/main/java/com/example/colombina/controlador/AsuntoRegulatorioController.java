package com.example.colombina.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.colombina.entidad.AsuntoRegulatorio;
import com.example.colombina.servicio.AsuntoRegulatorioService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/asuntos")
public class AsuntoRegulatorioController {

    @Autowired
    private AsuntoRegulatorioService asuntoRegulatorioService;

    // Crear un nuevo asunto regulatorio
    @PostMapping
    public ResponseEntity<AsuntoRegulatorio> crearAsuntoRegulatorio(@RequestBody AsuntoRegulatorio asuntoRegulatorio) {
        AsuntoRegulatorio nuevoAsunto = asuntoRegulatorioService.crearAsuntoRegulatorio(asuntoRegulatorio);
        return new ResponseEntity<>(nuevoAsunto, HttpStatus.CREATED);
    }

    // Obtener todos los asuntos regulatorios
    @GetMapping
    public ResponseEntity<List<AsuntoRegulatorio>> obtenerTodosLosAsuntosRegulatorios() {
        List<AsuntoRegulatorio> asuntos = asuntoRegulatorioService.obtenerTodosLosAsuntosRegulatorios();
        return new ResponseEntity<>(asuntos, HttpStatus.OK);
    }

    // Obtener un asunto regulatorio por ID
    @GetMapping("/{id}")
    public ResponseEntity<AsuntoRegulatorio> obtenerAsuntoRegulatorioPorId(@PathVariable Long id) {
        Optional<AsuntoRegulatorio> asunto = asuntoRegulatorioService.obtenerAsuntoRegulatorioPorId(id);
        return asunto.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Actualizar un asunto regulatorio
    @PutMapping("/{id}")
    public ResponseEntity<AsuntoRegulatorio> actualizarAsuntoRegulatorio(@PathVariable Long id, @RequestBody AsuntoRegulatorio asunto) {
        AsuntoRegulatorio asuntoActualizado = asuntoRegulatorioService.actualizarAsuntoRegulatorio(id, asunto);
        return ResponseEntity.ok(asuntoActualizado);
    }

    // Eliminar un asunto regulatorio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsuntoRegulatorio(@PathVariable Long id) {
        asuntoRegulatorioService.eliminarAsuntoRegulatorio(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar asuntos regulatorios por responsable
    @GetMapping("/responsable/{responsable}")
    public ResponseEntity<List<AsuntoRegulatorio>> buscarPorResponsable(@PathVariable String responsable) {
        List<AsuntoRegulatorio> asuntos = asuntoRegulatorioService.buscarPorResponsable(responsable);
        return new ResponseEntity<>(asuntos, HttpStatus.OK);
    }

    // Buscar asuntos regulatorios por estado
    @GetMapping("/estado/{estadoAsunto}")
    public ResponseEntity<List<AsuntoRegulatorio>> buscarPorEstadoAsunto(@PathVariable boolean estadoAsunto) {
        List<AsuntoRegulatorio> asuntos = asuntoRegulatorioService.buscarPorEstadoAsunto(estadoAsunto);
        return new ResponseEntity<>(asuntos, HttpStatus.OK);
    }

    // Buscar asuntos regulatorios por fecha
    @GetMapping("/fecha/{fechaAsunto}")
    public ResponseEntity<List<AsuntoRegulatorio>> buscarPorFechaAsunto(@PathVariable Date fechaAsunto) {
        List<AsuntoRegulatorio> asuntos = asuntoRegulatorioService.buscarPorFechaAsunto(fechaAsunto);
        return new ResponseEntity<>(asuntos, HttpStatus.OK);
    }
}

