package com.example.colombina.model;

import java.util.Arrays;

import org.springframework.http.MediaType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

@Getter
@AllArgsConstructor
public enum TipoDocumento {
    JPG("jpg", MediaType.IMAGE_JPEG),
    JPEG("jpeg", MediaType.IMAGE_JPEG),
    TXT("txt", MediaType.TEXT_PLAIN),
    PNG("png", MediaType.IMAGE_PNG),
    PDF("pdf", MediaType.APPLICATION_PDF);
    

    private final String extension;

    // Media type associated with the file extension
    private final MediaType mediaType;

    // Method to get MediaType based on the filename's extension
    public static MediaType fromFilename(String fileName) {
        // Finding the last index of '.' to get the extension
        val dotIndex = fileName.lastIndexOf('.');
        // Extracting file extension from filename
        val fileExtension = (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        // Finding matching enum constant for the file extension
        return Arrays.stream(values())
                .filter(e -> e.getExtension().equals(fileExtension))
                .findFirst()
                .map(TipoDocumento::getMediaType)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
    }
}
