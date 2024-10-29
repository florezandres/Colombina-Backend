package com.example.colombina.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Getter
@Setter
public class DocumentoDTO {
    private Long id;
    private String tipo;
    private boolean aprobado;
    private String tempUrl;  // Ruta temporal para acceso al archivo
    private LocalDate fechaExpiracion;
    private boolean cumpleNormativas;
    private String name;     // Nombre descriptivo del archivo o documento
    private MultipartFile file;
}
