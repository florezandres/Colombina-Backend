package com.example.colombina.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class DocumentoDTO {
    private String name;
    private MultipartFile file;  // Localización temporal del archivo
}
