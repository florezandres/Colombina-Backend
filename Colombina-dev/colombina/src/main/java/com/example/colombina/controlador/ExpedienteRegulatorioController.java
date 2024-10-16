package com.example.colombina.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.colombina.entidad.ExpedienteRegulatorio;
import com.example.colombina.servicio.ExpedienteRegulatorioService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expedientes")
public class ExpedienteRegulatorioController {

    @Autowired
    private ExpedienteRegulatorioService expedienteRegulatorioService;

    // Crear un nuevo expediente regulatorio
    @PostMapping
    public ResponseEntity<ExpedienteRegulatorio> crearExpedienteRegulatorio(@RequestBody ExpedienteRegulatorio expedienteRegulatorio) {
        ExpedienteRegulatorio nuevoExpediente = expedienteRegulatorioService.crearExpedienteRegulatorio(expedienteRegulatorio);
        return new ResponseEntity<>(nuevoExpediente, HttpStatus.CREATED);
    }

    // Obtener todos los expedientes regulatorios
    @GetMapping
    public ResponseEntity<List<ExpedienteRegulatorio>> obtenerTodosLosExpedientesRegulatorios() {
        List<ExpedienteRegulatorio> expedientes = expedienteRegulatorioService.obtenerTodosLosExpedientesRegulatorios();
        return new ResponseEntity<>(expedientes, HttpStatus.OK);
    }

    // Obtener un expediente regulatorio por ID
    @GetMapping("/{id}")
    public ResponseEntity<ExpedienteRegulatorio> obtenerExpedienteRegulatorioPorId(@PathVariable Long id) {
        Optional<ExpedienteRegulatorio> expediente = expedienteRegulatorioService.obtenerExpedienteRegulatorioPorId(id);
        return expediente.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Actualizar un expediente regulatorio
    @PutMapping("/{id}")
    public ResponseEntity<ExpedienteRegulatorio> actualizarExpedienteRegulatorio(@PathVariable Long id, @RequestBody ExpedienteRegulatorio expediente) {
        ExpedienteRegulatorio expedienteActualizado = expedienteRegulatorioService.actualizarExpedienteRegulatorio(id, expediente);
        return ResponseEntity.ok(expedienteActualizado);
    }

    // Eliminar un expediente regulatorio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarExpedienteRegulatorio(@PathVariable Long id) {
        expedienteRegulatorioService.eliminarExpedienteRegulatorio(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar expedientes por número de expediente
    @GetMapping("/numero/{numeroExpediente}")
    public ResponseEntity<List<ExpedienteRegulatorio>> buscarPorNumeroExpediente(@PathVariable int numeroExpediente) {
        List<ExpedienteRegulatorio> expedientes = expedienteRegulatorioService.buscarPorNumeroExpediente(numeroExpediente);
        return new ResponseEntity<>(expedientes, HttpStatus.OK);
    }

    // Buscar expedientes por estado
    @GetMapping("/estado/{estadoExpediente}")
    public ResponseEntity<List<ExpedienteRegulatorio>> buscarPorEstadoExpediente(@PathVariable boolean estadoExpediente) {
        List<ExpedienteRegulatorio> expedientes = expedienteRegulatorioService.buscarPorEstadoExpediente(estadoExpediente);
        return new ResponseEntity<>(expedientes, HttpStatus.OK);
    }

    // Buscar expedientes por fecha de creación
    @GetMapping("/fecha/{fechaCreacion}")
    public ResponseEntity<List<ExpedienteRegulatorio>> buscarPorFechaCreacion(@PathVariable Date fechaCreacion) {
        List<ExpedienteRegulatorio> expedientes = expedienteRegulatorioService.buscarPorFechaCreacion(fechaCreacion);
        return new ResponseEntity<>(expedientes, HttpStatus.OK);
    }

    // Buscar expedientes por descripción
    @GetMapping("/descripcion/{keyword}")
    public ResponseEntity<List<ExpedienteRegulatorio>> buscarPorDescripcion(@PathVariable String keyword) {
        List<ExpedienteRegulatorio> expedientes = expedienteRegulatorioService.buscarPorDescripcion(keyword);
        return new ResponseEntity<>(expedientes, HttpStatus.OK);
    }
}

