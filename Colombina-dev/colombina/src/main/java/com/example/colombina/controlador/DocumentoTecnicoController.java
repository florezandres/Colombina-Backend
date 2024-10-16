package com.example.colombina.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.colombina.entidad.DocumentoTecnico;
import com.example.colombina.servicio.DocumentoTecnicoService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoTecnicoController {

    @Autowired
    private DocumentoTecnicoService documentoTecnicoService;

    // Crear un nuevo documento técnico
    @PostMapping
    public ResponseEntity<DocumentoTecnico> crearDocumentoTecnico(@RequestBody DocumentoTecnico documentoTecnico) {
        DocumentoTecnico nuevoDocumento = documentoTecnicoService.crearDocumentoTecnico(documentoTecnico);
        return new ResponseEntity<>(nuevoDocumento, HttpStatus.CREATED);
    }

    // Obtener todos los documentos técnicos
    @GetMapping
    public ResponseEntity<List<DocumentoTecnico>> obtenerTodosLosDocumentosTecnicos() {
        List<DocumentoTecnico> documentos = documentoTecnicoService.obtenerTodosLosDocumentosTecnicos();
        return new ResponseEntity<>(documentos, HttpStatus.OK);
    }

    // Obtener un documento técnico por ID
    @GetMapping("/{id}")
    public ResponseEntity<DocumentoTecnico> obtenerDocumentoTecnicoPorId(@PathVariable Long id) {
        Optional<DocumentoTecnico> documento = documentoTecnicoService.obtenerDocumentoTecnicoPorId(id);
        return documento.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Actualizar un documento técnico
    @PutMapping("/{id}")
    public ResponseEntity<DocumentoTecnico> actualizarDocumentoTecnico(@PathVariable Long id, @RequestBody DocumentoTecnico documento) {
        DocumentoTecnico documentoActualizado = documentoTecnicoService.actualizarDocumentoTecnico(id, documento);
        return ResponseEntity.ok(documentoActualizado);
    }

    // Eliminar un documento técnico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDocumentoTecnico(@PathVariable Long id) {
        documentoTecnicoService.eliminarDocumentoTecnico(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar documentos por tipo de documento
    @GetMapping("/tipo/{tipoDocumento}")
    public ResponseEntity<List<DocumentoTecnico>> buscarPorTipoDocumento(@PathVariable String tipoDocumento) {
        List<DocumentoTecnico> documentos = documentoTecnicoService.buscarPorTipoDocumento(tipoDocumento);
        return new ResponseEntity<>(documentos, HttpStatus.OK);
    }

    // Buscar documentos por fecha de adjunción
    @GetMapping("/fecha/{fechaAdjuncion}")
    public ResponseEntity<List<DocumentoTecnico>> buscarPorFechaAdjuncion(@PathVariable Date fechaAdjuncion) {
        List<DocumentoTecnico> documentos = documentoTecnicoService.buscarPorFechaAdjuncion(fechaAdjuncion);
        return new ResponseEntity<>(documentos, HttpStatus.OK);
    }

    // Buscar documentos por estado
    @GetMapping("/estado/{estadoDocumento}")
    public ResponseEntity<List<DocumentoTecnico>> buscarPorEstadoDocumento(@PathVariable boolean estadoDocumento) {
        List<DocumentoTecnico> documentos = documentoTecnicoService.buscarPorEstadoDocumento(estadoDocumento);
        return new ResponseEntity<>(documentos, HttpStatus.OK);
    }
}

