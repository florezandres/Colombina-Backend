package com.example.colombina.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.colombina.DTOs.EstadisticasDTO;
import com.example.colombina.DTOs.TramiteDTO;
import com.example.colombina.model.Seguimiento;
import com.example.colombina.model.Tramite;
import com.example.colombina.repositories.SeguimientoRepository;
import com.example.colombina.services.DocumentoService;
import com.example.colombina.services.NotificacionService;
import com.example.colombina.services.TramiteService;

import java.util.Date;

@RestController
@RequestMapping("/tramites")
public class TramiteController {

    @Autowired
    private TramiteService tramiteService;

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private NotificacionService notificacionService;

    // Validación automática de documentos
    @GetMapping("/{idTramite}/validar-documentos")
    public ResponseEntity<?> validarDocumentos(@PathVariable Long idTramite) {
        try {
            documentoService.validarDocumentos(idTramite);
            return ResponseEntity.ok("Validación de documentos completada.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en la validación de documentos.");
        }
    }

    // Apertura de un trámite por su ID -> ASUNTOS REGULATORIOS
    @CrossOrigin
    @PostMapping("/{idTramite}/apertura")
    public ResponseEntity<?> abrirTramite(@PathVariable Long idTramite) {
        try {
            // Llamar al servicio para abrir el trámite
            tramiteService.abrirTramite(idTramite);
            notificacionService.enviarNotificacionEstadoTramite(idTramite); // Enviar notificación de cambio de estado
            return ResponseEntity.ok("Trámite abierto correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage()); // Error si el trámite no se encuentra
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al abrir el trámite.");
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

    // Traer todos los trámites
    @CrossOrigin
    @GetMapping("/todos")
    public ResponseEntity<?> findAll() {
        try {
            List<TramiteDTO> tramites = tramiteService.findAll();
            return ResponseEntity.ok(tramites);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al traer todos los trámites.");
        }
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            TramiteDTO tramite = tramiteService.findById(id);
            return ResponseEntity.ok(tramite);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al traer el trámite.");
        }
    }

    // HU-39 - Filtrar trámites por estado
    // Rol que utiliza el método: SOLICITANTE
    @CrossOrigin
    @GetMapping("/filtrado/estado")
    public ResponseEntity<?> filtrarTramitesPorEstado(@RequestParam Tramite.EstadoTramite estado) {
        try {
            List<TramiteDTO> tramitesFiltrados = tramiteService.filtrarTramitesPorEstado(estado);
            return ResponseEntity.ok(tramitesFiltrados);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al filtrar los trámites.");
        }
    }

    // HU-13 - Obtener seguimiento de un trámite
    @GetMapping("/{idTramite}/seguimiento")
    public ResponseEntity<?> obtenerSeguimiento(@PathVariable Long idTramite) {
        List<Seguimiento> seguimientos = seguimientoRepository.findByTramiteId(idTramite);
        return ResponseEntity.ok(seguimientos);
    }

    // HU-46 - Validación Automática de Documentos y Consolidación
    @CrossOrigin
    @PostMapping("/{idTramite}/consolidacion")
    public ResponseEntity<?> consolidarTramite(@PathVariable Long idTramite) {
        try {
            // Validación automática de documentos antes de la consolidación
            boolean validacionExitosa = documentoService.validarDocumentos(idTramite);

            if (!validacionExitosa) {
                return ResponseEntity.status(400).body(
                        "Existen documentos faltantes o incorrectos. Por favor, corrija los documentos antes de continuar.");
            }

            // Si los documentos son válidos, proceder con la consolidación del trámite
            tramiteService.consolidarTramite(idTramite);
            return ResponseEntity.ok("Consolidación completada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al consolidar el trámite.");
        }
    }

    // HU-35 - Modificar un tramite
    @PostMapping("/{idTramite}/modificar")
    public ResponseEntity<?> modificarTramite(@PathVariable Long idTramite, @RequestParam String nuevoEstado) {
        try {
            tramiteService.modificarTramite(idTramite, nuevoEstado);
            return ResponseEntity.ok("Trámite modificado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al modificar el trámite.");
        }
    }

    // HU-38 - Subir múltiples archivos para un trámite
    @PostMapping("/{idTramite}/subir-archivos")
    public ResponseEntity<?> subirArchivos(@PathVariable Long idTramite,
            @RequestParam("archivos") MultipartFile[] archivos) {
        try {
            // List<String> resultado = documentoService.guardarDocumentos(idTramite,
            // archivos);
            return ResponseEntity.ok("Archivos subidos correctamente: ");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al subir los archivos.");
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

    

  

    /*
     * //HU 17
     * 
     * @PostMapping("/{idTramite}/escalar")
     * public ResponseEntity<?> escalarTramite(@PathVariable Long idTramite) {
     * try {
     * // Escalar el trámite y crear la notificación
     * tramiteService.escalarTramite(idTramite);
     * return ResponseEntity.ok("El trámite ha sido escalado correctamente.");
     * } catch (Exception e) {
     * return ResponseEntity.status(500).body("Error al escalar el trámite.");
     * }
     * }
     */
}
