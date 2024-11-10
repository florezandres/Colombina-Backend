package com.example.colombina.controllers;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import com.example.colombina.model.Documento;
import com.example.colombina.model.TipoDocumento;
import com.example.colombina.model.Tramite;
import com.example.colombina.repositories.DocumentoRepository;
import com.example.colombina.services.AwsS3Service;
import com.example.colombina.services.DocumentoService;

@RestController
@RequestMapping("/files")
public class FilesController {

    private static final Logger log = LoggerFactory.getLogger(FilesController.class);

    @Autowired
    private AwsS3Service awsS3Service;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private DocumentoService documentoService;

    @CrossOrigin
    @PostMapping(value = "/subir-archivo/{idTramite}")
    public ResponseEntity<?> subirArchivo(
            @PathVariable Long idTramite,
            @ModelAttribute DocumentoDTO documentoDTO) {
        try {
            awsS3Service.uploadFile(documentoDTO.getName(), documentoDTO.getFile(), idTramite);
            documentoRepository.save(new Documento(documentoDTO.getName(), false, new Tramite(idTramite)));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error al subir archivo para el trámite " + idTramite, e);
            return ResponseEntity.status(500).body("Error al subir el archivo.");
        }
    }

    @CrossOrigin
    @PostMapping("/aprobar-documento/{idTramite}/{nombreDocumento}")
    public ResponseEntity<?> aprobarDocumento(@PathVariable Long idTramite, @PathVariable String nombreDocumento) {
        System.out.println("Aprobando documento " + nombreDocumento + " para trámite " + idTramite);
        Optional<Documento> documento = documentoRepository.findByTramiteIdAndNombre(idTramite, nombreDocumento);
        if (documento.isEmpty()) {
            return ResponseEntity.status(404).body("Documento no encontrado.");
        }
        Documento realDocumento = documento.get();
        realDocumento.setAprobado(true);
        documentoRepository.save(realDocumento);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/negar-documento/{idTramite}/{nombreDocumento}")
    public ResponseEntity<?> negarDocumento(@PathVariable Long idTramite, @PathVariable String nombreDocumento) {
        System.out.println("Negando documento " + nombreDocumento + " para trámite " + idTramite);
        Optional<Documento> documento = documentoRepository.findByTramiteIdAndNombre(idTramite, nombreDocumento);
        if (documento.isEmpty()) {
            return ResponseEntity.status(404).body("Documento no encontrado.");
        }
        Documento realDocumento = documento.get();
        realDocumento.setAprobado(false); 
        documentoRepository.save(realDocumento);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idTramite}/aprobados")
    public ResponseEntity<?> aprobados(@PathVariable Long idTramite) {
        return ResponseEntity.ok(Map.of("aprobados",
                documentoRepository.countByTramiteIdAndAprobadoTrue(idTramite), "total",
                documentoRepository.countByTramiteId(idTramite)));
    }

    @GetMapping("/{idTramite}/{nombreDocumento}")
    public ResponseEntity<?> getDocumento(@PathVariable Long idTramite, @PathVariable String nombreDocumento) {
        Optional<Documento> documento = documentoRepository.findByTramiteIdAndNombre(idTramite, nombreDocumento);
        if (documento.isEmpty()) {
            return ResponseEntity.status(404).body("Documento no encontrado.");
        }
        return ResponseEntity.ok(documento.get());
    }

    @CrossOrigin
    @GetMapping("/listar-archivos/{idTramite}")
    public ResponseEntity<List<?>> obtenerArchivos(@PathVariable Long idTramite) {
        try {
            List<Documento> archivos = documentoService.getDocumentsByTramiteId(idTramite);
            return ResponseEntity.status(200).body(archivos);
        } catch (Exception e) {
            log.error("Error al obtener archivos para el trámite " + idTramite, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    

    @GetMapping("/descargar-archivo/{idTramite}/{filename}")
    public ResponseEntity<InputStreamResource> descargarArchivo(
            @PathVariable Long idTramite,
            @PathVariable String filename) {

        // Aquí, asegúrate de que estás obteniendo el archivo correcto desde MinIO
        try {
            InputStream inputStream = awsS3Service.getObject(filename, idTramite);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(TipoDocumento.fromFilename(filename))
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @CrossOrigin
    @DeleteMapping("/eliminar-archivo/{idTramite}/{filename}")
    public ResponseEntity<?> eliminarArchivo(
            @PathVariable Long idTramite,
            @PathVariable String filename) {
        try {
            awsS3Service.deleteFile(filename, idTramite);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error al eliminar el archivo " + filename + " para el trámite " + idTramite, e);
            return ResponseEntity.status(500).body("No se pudo eliminar el archivo");
        }
    }
}