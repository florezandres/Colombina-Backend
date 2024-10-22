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

    @Autowired
    private NotificacionService notificacionService;

    // Lista de tipos de documentos requeridos para validar su presencia
    private static final List<String> DOCUMENTOS_REQUERIDOS = List.of("Documento A", "Documento B", "Documento C");

    // Verifica si todos los documentos de un trámite están aprobados
    public boolean verificarDocumentosCompletos(Long tramiteId) {
        List<Documento> documentos = documentoRepository.findByTramiteId(tramiteId);
        return documentos.stream().allMatch(Documento::isAprobado);
    }

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
