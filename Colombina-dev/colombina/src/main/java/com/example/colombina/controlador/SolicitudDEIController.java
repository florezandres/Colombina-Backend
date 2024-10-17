package com.example.colombina.controlador;

import com.example.colombina.entidad.Documento;
import com.example.colombina.entidad.Tramite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.colombina.entidad.SolicitudDEI;
import com.example.colombina.servicio.SolicitudDEIService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudDEIController {
    private final String DIRECTORIO_UPLOAD = System.getProperty("java.io.tmpdir");

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

    //CU 1 SOLICITUD DE TRÁMITE NACIONAL------------------------------------
    // Subida de archivos para una solicitud
    @PostMapping("/{solicitudId}/subir-archivos")
    public ResponseEntity<String> subirArchivos(@PathVariable Long solicitudId, @RequestParam("archivos") MultipartFile[] archivos) {
        String mensaje = solicitudDEIService.subirArchivos(solicitudId, archivos);
        return ResponseEntity.ok(mensaje);
    }

    // Obtener los documentos de una solicitud
    @GetMapping("/{solicitudId}/documentos")
    public ResponseEntity<List<String>> obtenerDocumentos(@PathVariable Long solicitudId) {
        List<String> documentos = solicitudDEIService.obtenerDocumentosDeSolicitud(solicitudId);
        return ResponseEntity.ok(documentos);
    }
}

