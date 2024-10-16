package com.example.colombina.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.colombina.entidad.Tramite;
import com.example.colombina.servicio.TramiteService;

@RestController
@RequestMapping("/api/tramites")
public class TramiteController {

    private final TramiteService tramiteService;

    public TramiteController(TramiteService tramiteService) {
        this.tramiteService = tramiteService;
    }

    // Crear un nuevo trámite
    @PostMapping
    public ResponseEntity<Tramite> crearTramite(@RequestBody Tramite tramite) {
        Tramite nuevoTramite = tramiteService.crearTramite(tramite);
        return new ResponseEntity<>(nuevoTramite, HttpStatus.CREATED);
    }

    // Obtener todos los trámites
    @GetMapping
    public ResponseEntity<List<Tramite>> obtenerTodosLosTramites() {
        List<Tramite> tramites = tramiteService.obtenerTodosLosTramites();
        return new ResponseEntity<>(tramites, HttpStatus.OK);
    }

    // Obtener trámite por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tramite> obtenerTramitePorId(@PathVariable Long id) {
        Optional<Tramite> tramite = tramiteService.obtenerTramitePorId(id);
        return tramite
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Actualizar trámite
    @PutMapping("/{id}")
    public ResponseEntity<Tramite> actualizarTramite(@PathVariable Long id, @RequestBody Tramite detallesTramite) {
        try {
            Tramite tramiteActualizado = tramiteService.actualizarTramite(id, detallesTramite);
            return new ResponseEntity<>(tramiteActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar trámite
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTramite(@PathVariable Long id) {
        try {
            tramiteService.eliminarTramite(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
