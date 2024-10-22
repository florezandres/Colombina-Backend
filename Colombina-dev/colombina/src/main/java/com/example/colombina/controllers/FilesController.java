package com.example.colombina.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.colombina.DTOs.DocumentoDTO;
import com.example.colombina.services.DocumentoService;
import com.example.colombina.services.MinioService;

@RestController
@RequestMapping("/files")
public class FilesController {

    private static final Logger log = LoggerFactory.getLogger(FilesController.class);

    @Autowired
    private MinioService minioService;

    @Autowired
    private DocumentoService documentoService;

    @CrossOrigin
    @PostMapping(value = "/subir-archivo/{id_tramite}")
    public ResponseEntity<?> subirArchivo(
            @PathVariable Long id_tramite,
            @ModelAttribute DocumentoDTO documentoDTO
    ) {
        try {
            minioService.uploadFile(documentoDTO.getName(), documentoDTO.getFile(), id_tramite);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error al subir archivo para el trámite " + id_tramite, e);
            return ResponseEntity.status(500).body("Error al subir el archivo.");
        }
    }

    @CrossOrigin
    @GetMapping("/listar-archivos/{id_tramite}")
    public ResponseEntity<List<String>> obtenerArchivos(@PathVariable Long id_tramite) {
        try {
            List<String> archivos = minioService.listFiles(id_tramite);
            return ResponseEntity.ok(archivos);
        } catch (Exception e) {
            log.error("Error al obtener archivos para el trámite " + id_tramite, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @CrossOrigin
    @DeleteMapping("/eliminar-archivo/{id_tramite}/{filename}")
    public ResponseEntity<?> eliminarArchivo(
            @PathVariable Long id_tramite,
            @PathVariable String filename) {
        try {
            minioService.deleteFile(filename, id_tramite);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error al eliminar el archivo " + filename + " para el trámite " + id_tramite, e);
            return ResponseEntity.status(500).body("No se pudo eliminar el archivo");
        }
    }

    // Endpoint para validar documentos
    @CrossOrigin
    @GetMapping("/validar-documentos/{id_tramite}")
    public ResponseEntity<?> validarDocumentos(@PathVariable Long id_tramite) {
        try {
            documentoService.validarDocumentos(id_tramite);
            return ResponseEntity.ok("Validación completada.");
        } catch (Exception e) {
            log.error("Error al validar los documentos para el trámite " + id_tramite, e);
            return ResponseEntity.status(500).body("Error en la validación de documentos.");
        }
    }
}
