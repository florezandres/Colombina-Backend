package com.example.colombina.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.colombina.model.Tramite;
import com.example.colombina.repositories.TramiteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.colombina.model.Documento;
import com.example.colombina.repositories.FileRepository;

import static com.example.colombina.model.Tramite.EstadoTramite.EN_REVISION;
import static com.example.colombina.model.Tramite.EstadoTramite.PENDIENTE;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private TramiteService tramiteService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TramiteRepository tramiteRepository;


    public List<Documento> getDocumentosCorregir(Long tramiteId) {
        // Verificar si el trámite existe y si está en estado PENDIENTE
        Optional<Tramite> tramiteOpt = tramiteRepository.findById(tramiteId);

        // Si el tramite no existe o no está en estado PENDIENTE, devolver lista vacía
        if (tramiteOpt.isEmpty() || tramiteOpt.get().getEstado() != PENDIENTE) {
            return List.of();
        }

        // Si el trámite está en estado PENDIENTE, obtener documentos con aprobado en false
        return fileRepository.findByTramiteId(tramiteId).stream()
                .filter(documento -> !documento.getAprobado())
                .collect(Collectors.toList());
    }

    public List<Documento> getDocumentsByTramiteId(Long tramiteId) {
        return fileRepository.findByTramiteId(tramiteId);
    }

    public String getComentario(Long idDocumento){
        Optional<Documento> documentoOpt = fileRepository.findById(idDocumento);
        return documentoOpt.get().getComentarios();
    }

    public String aprobarDocumento(Long idTramite, String nombreDocumento) {
        System.out.println("Entra a service aprobar documento");
        try {
            Optional<Documento> documentoOpt = findDocumento(idTramite, nombreDocumento);
            if (documentoOpt.isEmpty()) {
                return "Documento no encontrado.";
            }

            // Cambiar estado del trámite a EN_REVISION
            Tramite tramite = tramiteService.findByIdEntity(idTramite);
            tramite.setEstado(EN_REVISION);
            tramiteRepository.save(tramite);

            System.out.println("Tramite: " + tramite + "Documento: "+nombreDocumento);
            Documento documento = documentoOpt.get();
            documento.setAprobado(true);
            System.out.println("Documento: " + documento+" es true");
            fileRepository.save(documento);
            return "Documento aprobado exitosamente.";
        } catch (Exception e) {
            // Registrar el error en los logs y retornar un mensaje de error
            e.printStackTrace();
            return "Error interno al aprobar el documento.";
        }
    }

    public String negarDocumento(Long idTramite, String nombreDocumento, String comentario) {
        try {
            Optional<Documento> documentoOpt = findDocumento(idTramite, nombreDocumento);
            if (documentoOpt.isEmpty()) {
                return "Documento no encontrado.";
            }

            // Cambiar el estado del trámite
            Tramite tramite = tramiteService.findByIdEntity(idTramite);
            tramite.setEstado(PENDIENTE);
            tramiteRepository.save(tramite);

            // Guardar comentario y cambiar aprobación del documento
            Documento documento = documentoOpt.get();
            documento.setAprobado(false);
            documento.setComentarios(comentario); // Almacenar el comentario
            fileRepository.save(documento);

            return "Documento rechazado exitosamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error interno al rechazar el documento.";
        }
    }


    public Optional<Documento> findDocumento(Long idTramite, String nombreDocumento) {
        return fileRepository.findByTramiteIdAndNombre(idTramite, nombreDocumento);
    }
}
