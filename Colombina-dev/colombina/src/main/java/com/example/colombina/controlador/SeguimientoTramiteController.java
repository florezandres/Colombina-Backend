package com.example.colombina.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.colombina.entidad.SeguimientoTramite;
import com.example.colombina.servicio.SeguimientoTramiteService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seguimientos")
public class SeguimientoTramiteController {

    @Autowired
    private SeguimientoTramiteService seguimientoTramiteService;

    // Crear un nuevo seguimiento de trámite
    @PostMapping
    public ResponseEntity<SeguimientoTramite> crearSeguimientoTramite(@RequestBody SeguimientoTramite seguimientoTramite) {
        SeguimientoTramite nuevoSeguimiento = seguimientoTramiteService.crearSeguimientoTramite(seguimientoTramite);
        return new ResponseEntity<>(nuevoSeguimiento, HttpStatus.CREATED);
    }

    // Obtener todos los seguimientos de trámite
    @GetMapping
    public ResponseEntity<List<SeguimientoTramite>> obtenerTodosLosSeguimientos() {
        List<SeguimientoTramite> seguimientos = seguimientoTramiteService.obtenerTodosLosSeguimientos();
        return new ResponseEntity<>(seguimientos, HttpStatus.OK);
    }

    // Obtener un seguimiento de trámite por ID
    @GetMapping("/{id}")
    public ResponseEntity<SeguimientoTramite> obtenerSeguimientoTramitePorId(@PathVariable Long id) {
        Optional<SeguimientoTramite> seguimiento = seguimientoTramiteService.obtenerSeguimientoTramitePorId(id);
        return seguimiento.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Actualizar un seguimiento de trámite
    @PutMapping("/{id}")
    public ResponseEntity<SeguimientoTramite> actualizarSeguimientoTramite(@PathVariable Long id, @RequestBody SeguimientoTramite seguimiento) {
        SeguimientoTramite seguimientoActualizado = seguimientoTramiteService.actualizarSeguimientoTramite(id, seguimiento);
        return ResponseEntity.ok(seguimientoActualizado);
    }

    // Eliminar un seguimiento de trámite
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSeguimientoTramite(@PathVariable Long id) {
        seguimientoTramiteService.eliminarSeguimientoTramite(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar seguimientos por el estado actual
    @GetMapping("/estado/{estadoActual}")
    public ResponseEntity<List<SeguimientoTramite>> buscarPorEstadoActual(@PathVariable String estadoActual) {
        List<SeguimientoTramite> seguimientos = seguimientoTramiteService.buscarPorEstadoActual(estadoActual);
        return new ResponseEntity<>(seguimientos, HttpStatus.OK);
    }

    // Buscar seguimientos por fecha de seguimiento
    @GetMapping("/fecha/{fechaSeguimiento}")
    public ResponseEntity<List<SeguimientoTramite>> buscarPorFechaSeguimiento(@PathVariable Date fechaSeguimiento) {
        List<SeguimientoTramite> seguimientos = seguimientoTramiteService.buscarPorFechaSeguimiento(fechaSeguimiento);
        return new ResponseEntity<>(seguimientos, HttpStatus.OK);
    }

    // Buscar seguimientos por rango de fechas
    @GetMapping("/rango")
    public ResponseEntity<List<SeguimientoTramite>> buscarPorRangoDeFechas(@RequestParam Date fechaInicio, @RequestParam Date fechaFin) {
        List<SeguimientoTramite> seguimientos = seguimientoTramiteService.buscarPorRangoDeFechas(fechaInicio, fechaFin);
        return new ResponseEntity<>(seguimientos, HttpStatus.OK);
    }
}

