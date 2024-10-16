package com.example.colombina.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.colombina.entidad.SolicitudDEI;
import com.example.colombina.servicio.SolicitudDEIService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudDEIController {

    @Autowired
    private SolicitudDEIService solicitudDEIService;

    // Crear una nueva solicitud DEI
    @PostMapping
    public ResponseEntity<SolicitudDEI> crearSolicitudDEI(@RequestBody SolicitudDEI solicitudDEI) {
        SolicitudDEI nuevaSolicitud = solicitudDEIService.crearSolicitudDEI(solicitudDEI);
        return new ResponseEntity<>(nuevaSolicitud, HttpStatus.CREATED);
    }

    // Obtener todas las solicitudes DEI
    @GetMapping
    public ResponseEntity<List<SolicitudDEI>> obtenerTodasLasSolicitudesDEI() {
        List<SolicitudDEI> solicitudes = solicitudDEIService.obtenerTodasLasSolicitudesDEI();
        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
    }

    // Obtener una solicitud DEI por ID
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudDEI> obtenerSolicitudDEIPorId(@PathVariable Long id) {
        Optional<SolicitudDEI> solicitud = solicitudDEIService.obtenerSolicitudDEIPorId(id);
        return solicitud.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Actualizar una solicitud DEI
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudDEI> actualizarSolicitudDEI(@PathVariable Long id, @RequestBody SolicitudDEI solicitud) {
        SolicitudDEI solicitudActualizada = solicitudDEIService.actualizarSolicitudDEI(id, solicitud);
        return ResponseEntity.ok(solicitudActualizada);
    }

    // Eliminar una solicitud DEI
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSolicitudDEI(@PathVariable Long id) {
        solicitudDEIService.eliminarSolicitudDEI(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar solicitudes por el nombre del solicitante
    @GetMapping("/nombre/{nombreSolicitante}")
    public ResponseEntity<List<SolicitudDEI>> buscarPorNombreSolicitante(@PathVariable String nombreSolicitante) {
        List<SolicitudDEI> solicitudes = solicitudDEIService.buscarPorNombreSolicitante(nombreSolicitante);
        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
    }

    // Buscar solicitudes por tipo de producto
    @GetMapping("/tipo/{tipoDeProducto}")
    public ResponseEntity<List<SolicitudDEI>> buscarPorTipoDeProducto(@PathVariable String tipoDeProducto) {
        List<SolicitudDEI> solicitudes = solicitudDEIService.buscarPorTipoDeProducto(tipoDeProducto);
        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
    }

    // Buscar solicitudes por fecha de solicitud
    @GetMapping("/fecha/{fechaSolicitud}")
    public ResponseEntity<List<SolicitudDEI>> buscarPorFechaSolicitud(@PathVariable Date fechaSolicitud) {
        List<SolicitudDEI> solicitudes = solicitudDEIService.buscarPorFechaSolicitud(fechaSolicitud);
        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
    }
}

