package com.example.colombina.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.colombina.entidad.ArchivoControlDeTramites;
import com.example.colombina.servicio.ArchivoControlDeTramitesService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/archivos")
public class ArchivoControlDeTramitesController {

    @Autowired
    private ArchivoControlDeTramitesService archivoService;

    // Crear un nuevo archivo
    @PostMapping
    public ResponseEntity<ArchivoControlDeTramites> crearArchivo(@RequestBody ArchivoControlDeTramites archivo) {
        ArchivoControlDeTramites nuevoArchivo = archivoService.crearArchivo(archivo);
        return new ResponseEntity<>(nuevoArchivo, HttpStatus.CREATED);
    }

    // Obtener todos los archivos
    @GetMapping
    public ResponseEntity<List<ArchivoControlDeTramites>> obtenerTodosLosArchivos() {
        List<ArchivoControlDeTramites> archivos = archivoService.obtenerTodosLosArchivos();
        return new ResponseEntity<>(archivos, HttpStatus.OK);
    }

    // Obtener un archivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<ArchivoControlDeTramites> obtenerArchivoPorId(@PathVariable Long id) {
        Optional<ArchivoControlDeTramites> archivo = archivoService.obtenerArchivoPorId(id);
        return archivo.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Actualizar un archivo
    @PutMapping("/{id}")
    public ResponseEntity<ArchivoControlDeTramites> actualizarArchivo(@PathVariable Long id, @RequestBody ArchivoControlDeTramites archivo) {
        ArchivoControlDeTramites archivoActualizado = archivoService.actualizarArchivo(id, archivo);
        return ResponseEntity.ok(archivoActualizado);
    }

    // Eliminar un archivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArchivo(@PathVariable Long id) {
        archivoService.eliminarArchivo(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar archivos por estado del trámite
    @GetMapping("/estado/{estadoTramite}")
    public ResponseEntity<List<ArchivoControlDeTramites>> buscarPorEstadoTramite(@PathVariable boolean estadoTramite) {
        List<ArchivoControlDeTramites> archivos = archivoService.buscarPorEstadoTramite(estadoTramite);
        return new ResponseEntity<>(archivos, HttpStatus.OK);
    }

    // Buscar archivos por fecha de creación
    @GetMapping("/fecha/{fechaCreacion}")
    public ResponseEntity<List<ArchivoControlDeTramites>> buscarPorFechaCreacion(@PathVariable Date fechaCreacion) {
        List<ArchivoControlDeTramites> archivos = archivoService.buscarPorFechaCreacion(fechaCreacion);
        return new ResponseEntity<>(archivos, HttpStatus.OK);
    }

    // Buscar archivos por historial de modificaciones
    @GetMapping("/historial/{keyword}")
    public ResponseEntity<List<ArchivoControlDeTramites>> buscarPorHistorialDeModificaciones(@PathVariable String keyword) {
        List<ArchivoControlDeTramites> archivos = archivoService.buscarPorHistorialDeModificaciones(keyword);
        return new ResponseEntity<>(archivos, HttpStatus.OK);
    }
}
