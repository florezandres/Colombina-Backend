package com.example.colombina.controllers;

import com.example.colombina.DTOs.DocumentoDTO;
import com.example.colombina.services.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    /**
     * Lista todos los documentos en el sistema.
     *
     * @return Lista de DocumentoDTO.
     */
    @GetMapping("/todos")
    public List<DocumentoDTO> listarDocumentos() {
        return documentoService.recuperarTodoDocumento();
    }

    /**
     * Recupera un documento específico por ID.
     *
     * @param id ID del documento.
     * @return DocumentoDTO si se encuentra el documento, o null.
     */
    @GetMapping("/{id}")
    public DocumentoDTO verDocumento(@PathVariable Long id) {
        return documentoService.verDocumento(id);
    }

    /**
     * Actualiza el estado de aprobación de un documento.
     *
     * @param id       ID del documento.
     * @param aprobado Nuevo estado de aprobación.
     * @return DocumentoDTO actualizado si existe, o respuesta Not Found.
     */
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<DocumentoDTO> actualizarAprobado(@PathVariable Long id, @RequestParam boolean aprobado) {
        DocumentoDTO documentoActualizado = documentoService.actualizarAprobado(id, aprobado);
        if (documentoActualizado != null) {
            return ResponseEntity.ok(documentoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Endpoint para subir múltiples documentos asociados a un trámite.
     *
     * @param tramiteId ID del trámite al cual se asociarán los documentos.
     * @param archivos  Archivos a subir.
     * @return Respuesta con la lista de nombres de archivos guardados o error.
     */
    @PostMapping("/subir/{tramiteId}")
    public ResponseEntity<?> subirDocumentos(@PathVariable Long tramiteId, @RequestParam("archivos") MultipartFile[] archivos) {
        try {
            List<String> nombresGuardados = documentoService.guardarDocumentos(tramiteId, archivos);
            return ResponseEntity.ok(nombresGuardados);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir los archivos.");
        }
    }

    /**
     * Endpoint para validar los documentos de un trámite específico.
     *
     * @param tramiteId ID del trámite cuyos documentos se validarán.
     * @return Respuesta con el estado de la validación.
     */
    @GetMapping("/validar/{tramiteId}")
    public ResponseEntity<String> validarDocumentos(@PathVariable Long tramiteId) {
        boolean validacionExitosa = documentoService.validarDocumentos(tramiteId);
        if (validacionExitosa) {
            return ResponseEntity.ok("Todos los documentos son válidos.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Algunos documentos no son válidos.");
        }
    }
}
