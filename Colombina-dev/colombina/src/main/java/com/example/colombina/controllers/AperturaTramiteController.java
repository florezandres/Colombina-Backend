package com.example.colombina.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.colombina.model.Tramite;
import com.example.colombina.services.TramiteService;
import org.springframework.beans.factory.annotation.Autowired;
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


/*@RestController
@RequestMapping("/api/apertura")
public class AperturaTramiteController {

    private final TramiteService tramiteService;

    public AperturaTramiteController(TramiteService tramiteService) {
        this.tramiteService = tramiteService;
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Tramite> obtenerTramiteParaApertura(@PathVariable Long id) {
        Optional<Tramite> tramite = tramiteService.findById(id);
        return tramite.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Tramite> modificarTramite(@PathVariable Long id, @RequestBody Tramite detallesTramite) {
        try {
            Tramite tramiteActualizado = tramiteService.modificarTramite(id, detallesTramite);
            return new ResponseEntity<>(tramiteActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
} */