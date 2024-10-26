package com.example.colombina.controllers;

import com.example.colombina.DTOs.DocumentoDTO;
import com.example.colombina.services.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {
    @Autowired
    private DocumentoService documentoService;

    @GetMapping("/todos")
    public List<DocumentoDTO> listarDocumentos(){
        return documentoService.recuperarTodoDocumento();
    }

    @GetMapping("/{id}")
    public DocumentoDTO verDocumento(@RequestParam Long id){
        return documentoService.verDocumento(id);
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<DocumentoDTO> actualizarAprobado(@PathVariable Long id, @RequestParam boolean aprobado) {
        DocumentoDTO documentoActualizado = documentoService.actualizarAprobado(id, aprobado);
        if (documentoActualizado != null) {
            return ResponseEntity.ok(documentoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
