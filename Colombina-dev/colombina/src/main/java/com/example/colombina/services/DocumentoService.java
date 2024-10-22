package com.example.colombina.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.colombina.model.Documento;
import com.example.colombina.repositories.DocumentoRepository;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;
    // HU 38 
    @Autowired
    private TramiteRepository tramiteRepository;
    //
    @Autowired
    private NotificacionService notificacionService;

    // Lista de tipos de documentos requeridos para validar su presencia
    private static final List<String> DOCUMENTOS_REQUERIDOS = List.of("Documento A", "Documento B", "Documento C");
    // HU 38
    // Tipos de archivo permitidos PDF
    private static final List<String> TIPOS_PERMITIDOS = List.of("application/pdf");
    ////
    // Verifica si todos los documentos de un trámite están aprobados
    public boolean verificarDocumentosCompletos(Long tramiteId) {
        List<Documento> documentos = documentoRepository.findByTramiteId(tramiteId);
        return documentos.stream().allMatch(Documento::isAprobado);
    }
    // HU 38 Subida de Múltiples Archivos
// Método para manejar la subida de múltiples archivos
    public List<String> guardarDocumentos(Long tramiteId, MultipartFile[] archivos) {
        List<String> nombresGuardados = new ArrayList<>();
        Tramite tramite = tramiteRepository.findById(tramiteId)
                .orElseThrow(() -> new IllegalArgumentException("Trámite no encontrado."));

        for (MultipartFile archivo : archivos) {
            // Validación de tamaño de archivo
            if (archivo.getSize() > MAX_FILE_SIZE) {
                throw new IllegalArgumentException("El archivo " + archivo.getOriginalFilename() + " excede el tamaño máximo permitido de 10 MB.");
            }

            // Validación de tipo de archivo
            if (!TIPOS_PERMITIDOS.contains(archivo.getContentType())) {
                throw new IllegalArgumentException("El archivo " + archivo.getOriginalFilename() + " tiene un formato no permitido.");
            }

            // Crear y guardar el documento en la base de datos
            try {
                Documento documento = new Documento();
                documento.setTramite(tramite);
                documento.setCumpleNormativas(true); // Se asume que el documento cumple normativas al ser subido
                documento.setAprobado(false); // Se marca como no aprobado inicialmente
                documentoRepository.save(documento);

                nombresGuardados.add(archivo.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar el archivo " + archivo.getOriginalFilename(), e);
            }
        }

        // Notificar que los archivos han sido subidos correctamente
        //Adecuacion con la clase NotificacionService Faltante 
        //notificacionService.enviarNotificacionDocumentosSubidos(tramiteId, nombresGuardados);
        return nombresGuardados;
    }

    // HU 38 Subida de Múltiples Archivos
    
    // Método para validar los documentos enviados y notificar en caso de problemas
    public boolean validarDocumentos(Long tramiteId) {
        List<Documento> documentos = documentoRepository.findByTramiteId(tramiteId);
        boolean todosDocumentosValidos = true;

        // Verificar presencia de todos los documentos requeridos
        for (String tipoRequerido : DOCUMENTOS_REQUERIDOS) {
            boolean presente = documentos.stream().anyMatch(d -> d.getTipo().equals(tipoRequerido));
            if (!presente) {
                String mensaje = "Falta el documento requerido: " + tipoRequerido;
                System.out.println(mensaje);
                notificacionService.enviarNotificacionDocumentosFaltantes(tramiteId);
                todosDocumentosValidos = false;
            }
        }

        // Verificar si los documentos presentes son válidos
        for (Documento documento : documentos) {
            if (!documento.isCumpleNormativas()) {
                String mensaje = "El documento " + documento.getTipo() + " no cumple con las normativas.";
                System.out.println(mensaje);
                notificacionService.enviarNotificacionDocumentosFaltantes(tramiteId);
                todosDocumentosValidos = false;
            }
            if (documento.isVencido()) {
                String mensaje = "El documento " + documento.getTipo() + " está vencido.";
                System.out.println(mensaje);
                notificacionService.enviarNotificacionDocumentosFaltantes(tramiteId);
                todosDocumentosValidos = false;
            }
        }

        return todosDocumentosValidos;
    }
}
