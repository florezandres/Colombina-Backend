package com.example.colombina.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.colombina.DTOs.VersionDocumentoDTO;
import com.example.colombina.services.VersionDocumentoService;

import java.util.List;

@RestController
@RequestMapping("/api/documentos/{documentoId}/versiones")
public class VersionDocumentoController {

    @Autowired
    private VersionDocumentoService versionDocumentoService;

    @GetMapping
    public List<VersionDocumentoDTO> obtenerVersiones(@PathVariable Long documentoId) {
        return versionDocumentoService.obtenerVersionesPorDocumento(documentoId);
    }

    @PostMapping
    public VersionDocumentoDTO crearVersion(@PathVariable Long documentoId, @RequestBody VersionDocumentoDTO dto) {
        return versionDocumentoService.crearNuevaVersion(dto, documentoId);
    }
}
