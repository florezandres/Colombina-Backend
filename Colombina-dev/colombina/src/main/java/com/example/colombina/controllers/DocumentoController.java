package com.example.colombina.controllers;

import com.example.colombina.DTOs.DocumentoDTO;
import com.example.colombina.services.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
