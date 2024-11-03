package com.example.colombina.services;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.colombina.DTOs.DocumentoDTO;
import com.example.colombina.model.Documento;
import com.example.colombina.model.Tramite;
import com.example.colombina.services.NotificacionService;
import com.example.colombina.repositories.DocumentoRepository;
import com.example.colombina.repositories.TramiteRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private TramiteRepository tramiteRepository;

    @Autowired
    private NotificacionService notificacionService;

    private static final List<String> DOCUMENTOS_REQUERIDOS = List.of("Documento A", "Documento B", "Documento C");
    private static final List<String> TIPOS_PERMITIDOS = List.of("application/pdf");

    // Límite de tamaño de archivo en bytes (2 GB)
    private static final long LIMITE_TAMANO_ARCHIVO = 2L * 1024 * 1024 * 1024; // 2 GB en bytes

    /**
     * Verifica si todos los documentos de un trámite están aprobados.
     *
     * @param tramiteId El ID del trámite a verificar.
     * @return true si todos los documentos están aprobados, de lo contrario false.
     */
    public boolean verificarDocumentosCompletos(Long tramiteId) {
        List<Documento> documentos = documentoRepository.findByTramiteId(tramiteId);
        return documentos.stream().allMatch(Documento::isAprobado);
    }

    /**
     * Guarda múltiples documentos asociados a un trámite.
     *
     * @param tramiteId El ID del trámite al que se asociarán los documentos.
     * @param archivos  Lista de archivos a guardar.
     * @return Lista de nombres de archivos guardados.
     * @throws IllegalArgumentException si algún archivo no cumple con el tipo o tamaño permitido.
     */
    public List<String> guardarDocumentos(Long tramiteId, MultipartFile[] archivos) {
        List<String> nombresGuardados = new ArrayList<>();
        Tramite tramite = tramiteRepository.findById(tramiteId)
                .orElseThrow(() -> new IllegalArgumentException("Trámite no encontrado."));

        for (MultipartFile archivo : archivos) {
            // Validación del tipo de archivo (solo permitidos archivos PDF)
            if (!TIPOS_PERMITIDOS.contains(archivo.getContentType())) {
                throw new IllegalArgumentException(
                        "El archivo " + archivo.getOriginalFilename() + " tiene un formato no permitido.");
            }

            // Validación del tamaño del archivo (no excede 2 GB)
            if (archivo.getSize() > LIMITE_TAMANO_ARCHIVO) {
                throw new IllegalArgumentException(
                        "El archivo " + archivo.getOriginalFilename() + " excede el tamaño máximo permitido de 2 GB.");
            }

            // Crear y configurar el documento a guardar
            Documento documento = new Documento();
            documento.setTramite(tramite);
            documento.setCumpleNormativas(true); // Asumimos que cumple las normativas al ser subido
            documento.setAprobado(false); // Marcado como no aprobado inicialmente
            documentoRepository.save(documento);

            nombresGuardados.add(archivo.getOriginalFilename());
        }

        // Retorna los nombres de los archivos guardados
        return nombresGuardados;
    }

    /**
     * Valida todos los documentos de un trámite para verificar que cumplan con las normativas
     * y estén vigentes. Envía notificaciones al usuario en caso de problemas.
     *
     * @param tramiteId El ID del trámite cuyos documentos se validarán.
     * @return true si todos los documentos son válidos, de lo contrario false.
     */
    public boolean validarDocumentos(Long tramiteId) {
        List<Documento> documentos = documentoRepository.findByTramiteId(tramiteId);
        List<String> documentosFaltantes = new ArrayList<>();
        boolean todosDocumentosValidos = true;

        // Verificar la presencia de todos los documentos requeridos
        for (String tipoRequerido : DOCUMENTOS_REQUERIDOS) {
            if (documentos.stream().noneMatch(d -> d.getTipo().equals(tipoRequerido))) {
                // Si falta un documento, agregarlo a la lista y notificar
                documentosFaltantes.add(tipoRequerido);
                System.out.println("Falta el documento requerido: " + tipoRequerido);
                todosDocumentosValidos = false;
            }
        }

        // Validar cumplimiento de normativas y vigencia
        for (Documento documento : documentos) {
            if (!documento.isCumpleNormativas()) {
                // Si no cumple normativas, agregar a la lista y notificar
                documentosFaltantes.add(documento.getTipo());
                System.out.println("El documento " + documento.getTipo() + " no cumple con las normativas.");
                todosDocumentosValidos = false;
            } else if (documento.getFechaExpiracion() != null && documento.getFechaExpiracion().isBefore(LocalDate.now())) {
                // Si está vencido, agregar a la lista y notificar
                documentosFaltantes.add(documento.getTipo());
                System.out.println("El documento " + documento.getTipo() + " está vencido.");
                todosDocumentosValidos = false;
            }
        }

        // Notificar documentos faltantes
        if (!documentosFaltantes.isEmpty()) {
            notificacionService.enviarNotificacionDocumentosFaltantes(tramiteId, documentosFaltantes);
        }

        return todosDocumentosValidos;
    }


    /**
     * Recupera todos los documentos en forma de DTO.
     *
     * @return Lista de Documentos como DocumentoDTO.
     */
    public List<DocumentoDTO> recuperarTodoDocumento(){
        ModelMapper modelMapper = new ModelMapper();
        List<Documento> documentos = documentoRepository.findAll();
        Type listType = new TypeToken<List<DocumentoDTO>>() {}.getType();
        return modelMapper.map(documentos, listType);
    }

    /**
     * Recupera un documento específico y lo convierte a DTO.
     *
     * @param id ID del documento a recuperar.
     * @return DocumentoDTO si se encuentra el documento, de lo contrario null.
     */
    public DocumentoDTO verDocumento(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Documento> documentoOptional = documentoRepository.findById(id);
        return documentoOptional
                .map(documento -> modelMapper.map(documento, DocumentoDTO.class))
                .orElse(null); // Devuelve null si el documento no existe
    }

    /**
     * Actualiza el estado de aprobación de un documento.
     *
     * @param id       ID del documento a actualizar.
     * @param aprobado Nuevo estado de aprobación.
     * @return DocumentoDTO actualizado, o null si no se encontró el documento.
     */
    public DocumentoDTO actualizarAprobado(Long id, boolean aprobado) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<Documento> documentoOptional = documentoRepository.findById(id);
        if (documentoOptional.isPresent()) {
            Documento documento = documentoOptional.get();
            documento.setAprobado(aprobado);
            Documento documentoActualizado = documentoRepository.save(documento);
            return modelMapper.map(documentoActualizado, DocumentoDTO.class);
        }
        return null;
    }
}
