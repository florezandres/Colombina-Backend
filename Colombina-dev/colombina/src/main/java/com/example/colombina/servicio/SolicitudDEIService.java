package com.example.colombina.servicio;

import com.example.colombina.repositorio.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.colombina.entidad.SolicitudDEI;
import com.example.colombina.repositorio.SolicitudDEIRepository;
import com.example.colombina.entidad.Tramite;
import com.example.colombina.entidad.Documento;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SolicitudDEIService {
    // Inyectar la ruta de subida de archivos desde el archivo de configuración
    @Value("${directorio.upload}")
    private String DIRECTORIO_UPLOAD;

    @Autowired
    private SolicitudDEIRepository solicitudDEIRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    // Crear una nueva solicitud DEI
    public SolicitudDEI crearSolicitudDEI(SolicitudDEI solicitudDEI) {
        return solicitudDEIRepository.save(solicitudDEI);
    }

    // Obtener todas las solicitudes DEI
    public List<SolicitudDEI> obtenerTodasLasSolicitudesDEI() {
        return solicitudDEIRepository.findAll();
    }

    // Obtener una solicitud DEI por ID
    public Optional<SolicitudDEI> obtenerSolicitudDEIPorId(Long id) {
        return solicitudDEIRepository.findById(id);
    }

    // Actualizar una solicitud DEI
    public SolicitudDEI actualizarSolicitudDEI(Long id, SolicitudDEI solicitudActualizada) {
        solicitudActualizada.setIdSolicitud(id);
        return solicitudDEIRepository.save(solicitudActualizada);
    }

    // Eliminar una solicitud DEI
    public void eliminarSolicitudDEI(Long id) {
        solicitudDEIRepository.deleteById(id);
    }

    // Buscar solicitudes por el nombre del solicitante
    /*public List<SolicitudDEI> buscarPorNombreSolicitante(String nombreSolicitante) {
        return solicitudDEIRepository.findByNombreSolicitante(nombreSolicitante);
    }*/

    // Buscar solicitudes por tipo de producto
    public List<SolicitudDEI> buscarPorTipoDeProducto(String tipoDeProducto) {
        return solicitudDEIRepository.findByTipoDeProducto(tipoDeProducto);
    }

    // Buscar solicitudes por fecha de solicitud
    public List<SolicitudDEI> buscarPorFechaSolicitud(Date fechaSolicitud) {
        return solicitudDEIRepository.findByFechaSolicitud(fechaSolicitud);
    }

    //CU 1 solicitud de trámite------------------------------------
    public SolicitudDEI obtenerSolicitudPorId(Long solicitudId) {
        return solicitudDEIRepository.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
    }

    public List<String> obtenerDocumentosDeSolicitud(Long solicitudId) {
        // Buscar la solicitud por ID
        SolicitudDEI solicitud = obtenerSolicitudPorId(solicitudId);

        // Obtener el tramite asociado a la solicitud
        Tramite tramite = solicitud.getTramite();

        // Obtener las rutas de los documentos
        return tramite.getDocumentos().stream()
                .map(Documento::getInformacion)  // Extraer la ruta del archivo
                .collect(Collectors.toList());
    }

    public String subirArchivos(Long solicitudId, MultipartFile[] archivos) {
        SolicitudDEI solicitud = obtenerSolicitudPorId(solicitudId);

        Tramite tramite = solicitud.getTramite();

        for (MultipartFile archivo : archivos) {
            try {
                // Guardar el archivo en una ubicación temporal
                String nombreArchivo = archivo.getOriginalFilename();
                Path rutaArchivo = Paths.get(DIRECTORIO_UPLOAD, nombreArchivo);
                Files.write(rutaArchivo, archivo.getBytes());

                // Crear un nuevo Documento con la ruta del archivo
                Documento documento = new Documento();
                documento.setInformacion(rutaArchivo.toString());
                documento.setTramite(tramite);

                // Guardar el documento en la base de datos
                documentoRepository.save(documento);

            } catch (IOException e) {
                throw new RuntimeException("Error al subir los archivos", e);
            }
        }

        return "Archivos subidos correctamente";
    }
}

