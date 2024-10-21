package com.example.colombina.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.colombina.DTOs.VersionDocumentoDTO;
import com.example.colombina.model.Documento;
import com.example.colombina.model.VersionDocumento;
import com.example.colombina.repositories.VersionDocumentoRepository;

@Service
public class VersionDocumentoService {

    @Autowired
    private VersionDocumentoRepository versionDocumentoRepository;

    public List<VersionDocumentoDTO> obtenerVersionesPorDocumento(Long documentoId) {
        List<VersionDocumento> versiones = versionDocumentoRepository.findByDocumentoId(documentoId);
        return versiones.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public VersionDocumentoDTO crearNuevaVersion(VersionDocumentoDTO dto, Long documentoId) {
        VersionDocumento version = new VersionDocumento();
        
        // Modificación del constructor de Documento para aceptar solo documentoId
        Documento documento = new Documento();
        documento.setId(documentoId); // Relación con el documento
        version.setDocumento(documento);
        
        version.setNombreArchivo(dto.getNombreArchivo());
        version.setUrlArchivo(dto.getUrlArchivo());
        version.setFechaCreacion(dto.getFechaCreacion());
        version.setCambiosDestacados(dto.getCambiosDestacados());

        VersionDocumento nuevaVersion = versionDocumentoRepository.save(version);
        return convertirADTO(nuevaVersion);
    }

    private VersionDocumentoDTO convertirADTO(VersionDocumento versionDocumento) {
        VersionDocumentoDTO dto = new VersionDocumentoDTO();
        dto.setId(versionDocumento.getId());
        dto.setNombreArchivo(versionDocumento.getNombreArchivo());
        dto.setUrlArchivo(versionDocumento.getUrlArchivo());
        dto.setFechaCreacion(versionDocumento.getFechaCreacion());
        dto.setCambiosDestacados(versionDocumento.getCambiosDestacados());
        return dto;
    }
}
