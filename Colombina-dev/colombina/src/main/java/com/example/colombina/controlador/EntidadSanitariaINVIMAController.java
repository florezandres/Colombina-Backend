package com.example.colombina.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.colombina.entidad.EntidadSanitariaINVIMA;
import com.example.colombina.servicio.EntidadSanitariaINVIMAService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entidades")
public class EntidadSanitariaINVIMAController {

    @Autowired
    private EntidadSanitariaINVIMAService entidadSanitariaINVIMAService;

    // Crear una nueva entidad sanitaria INVIMA
    @PostMapping
    public ResponseEntity<EntidadSanitariaINVIMA> crearEntidadSanitariaINVIMA(@RequestBody EntidadSanitariaINVIMA entidadSanitariaINVIMA) {
        EntidadSanitariaINVIMA nuevaEntidad = entidadSanitariaINVIMAService.crearEntidadSanitariaINVIMA(entidadSanitariaINVIMA);
        return new ResponseEntity<>(nuevaEntidad, HttpStatus.CREATED);
    }

    // Obtener todas las entidades sanitarias INVIMA
    @GetMapping
    public ResponseEntity<List<EntidadSanitariaINVIMA>> obtenerTodasLasEntidadesSanitariasINVIMA() {
        List<EntidadSanitariaINVIMA> entidades = entidadSanitariaINVIMAService.obtenerTodasLasEntidadesSanitariasINVIMA();
        return new ResponseEntity<>(entidades, HttpStatus.OK);
    }

    // Obtener una entidad sanitaria INVIMA por ID
    @GetMapping("/{id}")
    public ResponseEntity<EntidadSanitariaINVIMA> obtenerEntidadSanitariaINVIMAPorId(@PathVariable Long id) {
        Optional<EntidadSanitariaINVIMA> entidad = entidadSanitariaINVIMAService.obtenerEntidadSanitariaINVIMAPorId(id);
        return entidad.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Actualizar una entidad sanitaria INVIMA
    @PutMapping("/{id}")
    public ResponseEntity<EntidadSanitariaINVIMA> actualizarEntidadSanitariaINVIMA(@PathVariable Long id, @RequestBody EntidadSanitariaINVIMA entidad) {
        EntidadSanitariaINVIMA entidadActualizada = entidadSanitariaINVIMAService.actualizarEntidadSanitariaINVIMA(id, entidad);
        return ResponseEntity.ok(entidadActualizada);
    }

    // Eliminar una entidad sanitaria INVIMA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEntidadSanitariaINVIMA(@PathVariable Long id) {
        entidadSanitariaINVIMAService.eliminarEntidadSanitariaINVIMA(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar por resultado del trámite
    @GetMapping("/resultado/{resultadoTramite}")
    public ResponseEntity<List<EntidadSanitariaINVIMA>> buscarPorResultadoTramite(@PathVariable boolean resultadoTramite) {
        List<EntidadSanitariaINVIMA> entidades = entidadSanitariaINVIMAService.buscarPorResultadoTramite(resultadoTramite);
        return new ResponseEntity<>(entidades, HttpStatus.OK);
    }

    // Buscar por fecha de radicación
    @GetMapping("/fecha/{fechaRadicacion}")
    public ResponseEntity<List<EntidadSanitariaINVIMA>> buscarPorFechaRadicacion(@PathVariable Date fechaRadicacion) {
        List<EntidadSanitariaINVIMA> entidades = entidadSanitariaINVIMAService.buscarPorFechaRadicacion(fechaRadicacion);
        return new ResponseEntity<>(entidades, HttpStatus.OK);
    }

    // Buscar por comentarios que contengan una palabra clave
    @GetMapping("/comentarios/{keyword}")
    public ResponseEntity<List<EntidadSanitariaINVIMA>> buscarPorComentarios(@PathVariable String keyword) {
        List<EntidadSanitariaINVIMA> entidades = entidadSanitariaINVIMAService.buscarPorComentarios(keyword);
        return new ResponseEntity<>(entidades, HttpStatus.OK);
    }
}

