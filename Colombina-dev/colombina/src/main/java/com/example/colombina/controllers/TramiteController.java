package com.example.colombina.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.colombina.DTOs.InfoAperturaTramiteDTO;
import com.example.colombina.DTOs.InfoControlTramiteDTO;
import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.Seguimiento;
import com.example.colombina.repositories.SeguimientoRepository;
import com.example.colombina.services.TramiteService;

import java.util.Date;
import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/tramites")
public class TramiteController {

    @Autowired
    private TramiteService tramiteService;

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @PostMapping("/{idTramite}/apertura")
    public ResponseEntity<?> abrirTramite(@PathVariable Long idTramite,
            @RequestBody InfoAperturaTramiteDTO infoTramite) {
        try {
            // Llamar al servicio para abrir el trámite
            tramiteService.abrirTramite(idTramite, infoTramite);
            HashMap<String, String> map = new HashMap<>();
            map.put("message", "Trámite abierto correctamente.");
            return ResponseEntity.ok(map);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Error si el trámite no se encuentra
        }
    }

    @PostMapping("/{idTramite}/documentacion-revisada")
    public ResponseEntity<?> documentacionRevisada(@PathVariable Long idTramite) {
        try {
            tramiteService.documentacionRevisada(idTramite);
            HashMap<String, String> map = new HashMap<>();
            map.put("message", "Documentación revisada correctamente.");
            return ResponseEntity.ok(map);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/{idTramite}/info-control")
    public ResponseEntity<?> infoControl(@PathVariable Long idTramite, @RequestBody InfoControlTramiteDTO infoTramite) {
        try {
            tramiteService.infoControl(idTramite, infoTramite);
            HashMap<String, String> map = new HashMap<>();
            map.put("message", "Información de control registrada correctamente.");
            return ResponseEntity.ok(map);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // HU-43 - Elimina un trámite que esté incompleto
    // Rol que utiliza el método: ASUNTOSREG (Agente de la Agencia de Asuntos
    // Regulatorios)
    @DeleteMapping("/{idTramite}/eliminar")
    public ResponseEntity<?> eliminarTramite(@PathVariable Long idTramite) {
        try {
            tramiteService.eliminarTramite(idTramite);
            return ResponseEntity.ok("Trámite eliminado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar el trámite.");
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {
        try {
            List<TramiteDTO> tramites = tramiteService.findAll();
            return ResponseEntity.ok(tramites);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al traer todos los trámites.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        TramiteDTO tramite = tramiteService.findById(id);
        return ResponseEntity.ok(tramite);
    }

    // HU-13 - Obtener seguimiento de un trámite
    @GetMapping("/{idTramite}/seguimiento")
    public ResponseEntity<?> obtenerSeguimiento(@PathVariable Long idTramite) {
        List<Seguimiento> seguimientos = seguimientoRepository.findByTramiteId(idTramite);
        return ResponseEntity.ok(seguimientos);
    }

    // HU-35 - Modificar un tramite
    @PostMapping("/{idTramite}/modificar")
    public ResponseEntity<?> modificarTramite(@PathVariable Long idTramite, @RequestParam String nuevoEstado) {
        try {
            tramiteService.modificarTramite(idTramite, nuevoEstado);
            return ResponseEntity.ok("Trámite modificado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/{idTramite}/cambiar-etapa")
    public ResponseEntity<?> cambiarEtapa(@PathVariable Long idTramite, @RequestParam Integer nuevaEtapa) {
        try {
            tramiteService.cambiarEtapa(idTramite, nuevaEtapa);
            return ResponseEntity.ok("Etapa cambiada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    @CrossOrigin
    @PostMapping("/{idTramite}/aceptar")
    public ResponseEntity<?> aceptarTramite(
            @PathVariable Long idTramite,
            @RequestParam("numeroRadicado") String numeroRadicado,
            @RequestParam("llave") Double llave) {
        try {
            if (numeroRadicado == null || llave == null) {
                return ResponseEntity.status(400).body("Número de radicado y llave son requeridos.");
            }

            // Llamar al servicio para actualizar el trámite con el número de radicado y la llave
            tramiteService.asociarNumeroRadicadoYLLave(idTramite, numeroRadicado, llave);

            return ResponseEntity.ok(Collections.singletonMap("message", "Trámite aceptado y número de radicado asignado."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al aceptar el trámite.");
        }
    }

    // HU-53 - Generar reportes personalizados
    @GetMapping("/reportes/personalizados")
    public ResponseEntity<?> generarReportePersonalizado(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Date fechaInicio,
            @RequestParam(required = false) Date fechaFin) {
        try {
            List<TramiteDTO> reporte = tramiteService.generarReportePersonalizado(estado, fechaInicio, fechaFin);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al generar el reporte.");
        }
    }
}
