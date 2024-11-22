package com.example.colombina.controllers;

import java.io.InputStream;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.*;

import com.example.colombina.DTOs.DocumentoDTO;
import com.example.colombina.model.Documento;
import com.example.colombina.model.TipoDocumento;
import com.example.colombina.model.Tramite;
import com.example.colombina.repositories.FileRepository;
import com.example.colombina.services.AwsS3Service;
import com.example.colombina.services.FileService;

@RestController
@RequestMapping("/files")
public class FilesController {

    private static final Logger log = LoggerFactory.getLogger(FilesController.class);

    @Autowired
    private AwsS3Service awsS3Service;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @CrossOrigin
    @PostMapping(value = "/subir-archivo/{idTramite}")
    public ResponseEntity<?> subirArchivo(
            @PathVariable Long idTramite,
            @ModelAttribute DocumentoDTO documentoDTO) {
        try {
            awsS3Service.uploadFile(documentoDTO.getName(), documentoDTO.getFile(), idTramite);
            fileRepository.save(new Documento(documentoDTO.getName(), false, new Tramite(idTramite)));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error al subir archivo para el trámite " + idTramite, e);
            return ResponseEntity.status(500).body("Error al subir el archivo.");
        }
    }

    @CrossOrigin
    @PostMapping("/aprobar-documento/{idTramite}/{nombreDocumento}")
    public ResponseEntity<?> aprobarDocumento(@PathVariable Long idTramite, @PathVariable String nombreDocumento) {
        System.out.println("Entra a endpoint aprobar documento");
        String result = fileService.aprobarDocumento(idTramite, nombreDocumento);
        Map<String, String> response = new HashMap<>();
        if ("Documento no encontrado.".equals(result)) {
            response.put("error", result);
            return ResponseEntity.status(404).body(response); // Documento no encontrado
        } else if ("Error interno al aprobar el documento".equals(result)) {
            response.put("error", result);
            return ResponseEntity.status(500).body(response); // Error interno
        }

        response.put("message", result); // Respuesta de éxito en JSON
        return ResponseEntity.ok(response);
    }


    @CrossOrigin
    @GetMapping("/comentario-documento/{idDocumento}")
    public ResponseEntity<?> traerComentarios(@PathVariable Long idDocumento) {
        try {
            String comentarios = fileService.getComentario(idDocumento);
            return ResponseEntity.status(200).body(comentarios);
        } catch (Exception e) {
            log.error("Error al obtener comentarios para el trámite " + idDocumento, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @CrossOrigin
    @PostMapping("/negar-documento/{idTramite}/{nombreDocumento}")
    public ResponseEntity<?> negarDocumento(
            @PathVariable Long idTramite,
            @PathVariable String nombreDocumento,
            @RequestBody Map<String, String> body) { // Recibir cuerpo de la solicitud
        String comentario = body.get("comentario"); // Obtener el comentario del cuerpo
        String result = fileService.negarDocumento(idTramite, nombreDocumento, comentario); // Pasar el comentario

        Map<String, String> response = new HashMap<>();
        if ("Documento no encontrado.".equals(result)) {
            response.put("error", result);
            return ResponseEntity.status(404).body(response);
        } else if ("Error interno al rechazar el documento".equals(result)) {
            response.put("error", result);
            return ResponseEntity.status(500).body(response);
        }

        response.put("message", result); // Respuesta de éxito en JSON
        return ResponseEntity.ok(response);
    }



    @CrossOrigin
    @GetMapping("/documentos-a-corregir/{idTramite}")
    public ResponseEntity<?> getDocumentosCorregir(@PathVariable Long idTramite) {
        try {
            // Llamar al servicio para obtener los documentos que necesitan corrección
            List<Documento> documentosCorregir = fileService.getDocumentosCorregir(idTramite);

            // Verificar si no hay documentos pendientes de corrección
            if (documentosCorregir.isEmpty()) {
                return ResponseEntity.status(404).body(Map.of("message", "No hay documentos pendientes de corrección o el trámite no está en estado PENDIENTE."));
            }

            // Retornar la lista de documentos pendientes de corrección
            return ResponseEntity.ok(documentosCorregir);
        } catch (Exception e) {
            log.error("Error al obtener documentos para corregir para el trámite " + idTramite, e);
            return ResponseEntity.status(500).body(Map.of("error", "Error al obtener documentos para corregir."));
        }
    }

    @GetMapping("/{idTramite}/aprobados")
    public ResponseEntity<?> aprobados(@PathVariable Long idTramite) {
        return ResponseEntity.ok(Map.of("aprobados",
                fileRepository.countByTramiteIdAndAprobadoTrue(idTramite), "total",
                fileRepository.countByTramiteId(idTramite)));
    }

    @GetMapping("/{idTramite}/{nombreDocumento}")
    public ResponseEntity<?> getDocumento(@PathVariable Long idTramite, @PathVariable String nombreDocumento) {
        Optional<Documento> documento = fileRepository.findByTramiteIdAndNombre(idTramite, nombreDocumento);
        if (documento.isEmpty()) {
            return ResponseEntity.status(404).body("Documento no encontrado.");
        }
        return ResponseEntity.ok(documento.get());
    }

    @CrossOrigin
    @GetMapping("/listar-archivos/{idTramite}")
    public ResponseEntity<List<?>> obtenerArchivos(@PathVariable Long idTramite) {
        try {
            List<Documento> archivos = fileService.getDocumentsByTramiteId(idTramite);
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
    @DeleteMapping("/eliminar-archivo/{idTramite}/{filename}/{idDocumento}")
    public ResponseEntity<?> eliminarArchivo(
            @PathVariable Long idTramite,
            @PathVariable String filename,
            @PathVariable Long idDocumento) {
        System.out.println("Entra a endpoint eliminar archivo para eliminar: "+filename+ " del trámite: "+idTramite);
        try {
            awsS3Service.deleteFile(filename, idTramite);
            fileService.eliminarDocumento(idDocumento);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error al eliminar el archivo " + filename + " para el trámite " + idTramite, e);
            return ResponseEntity.status(500).body("No se pudo eliminar el archivo");
        }
    }
}