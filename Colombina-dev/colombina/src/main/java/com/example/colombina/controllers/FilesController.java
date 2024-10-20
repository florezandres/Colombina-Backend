package com.example.colombina.controllers;

import com.example.colombina.DTOs.DocumentoDTO;
import com.example.colombina.services.MinioService;
import io.minio.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FilesController {

    private static final Logger log = LoggerFactory.getLogger(FilesController.class);

    @Autowired
    private MinioService minioService;

    @CrossOrigin
    @PostMapping(value = "/subir-archivo/{id_tramite}")
    public ResponseEntity<?> subirArchivo(
            @PathVariable Long id_tramite,
            @ModelAttribute DocumentoDTO documentoDTO
    ) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioService.uploadFile(documentoDTO.getName(), documentoDTO.getFile(), id_tramite);
        return ResponseEntity.ok().build();
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
}
