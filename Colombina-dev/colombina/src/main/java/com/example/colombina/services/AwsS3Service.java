package com.example.colombina.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.colombina.DTOs.DocumentoDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AwsS3Service {

    @Autowired
    private AmazonS3 amazonS3Client;

    public String getOrCreateBucketForTramite(Long tramiteId) {
        String bucketName = "tramite-" + tramiteId;

        try {
            if (!amazonS3Client.doesBucketExistV2(bucketName)) {
                amazonS3Client.createBucket(bucketName);
                log.info("Bucket creado: " + bucketName);
            } else {
                log.info("Bucket ya existe: " + bucketName);
            }

            return bucketName;
        } catch (Exception e) {
            log.error("Error al verificar o crear el bucket para el trámite " + tramiteId, e);
            throw new RuntimeException("No se pudo crear o verificar el bucket", e);
        }
    }

    public InputStream getObject(String filename, Long tramiteId) {
        String bucketName = getOrCreateBucketForTramite(tramiteId);
        S3Object s3Object = amazonS3Client.getObject(bucketName, filename);
        return s3Object.getObjectContent();
    }

    public List<DocumentoDTO> listFiles(Long tramiteId) throws AmazonClientException {
        String bucketName = getOrCreateBucketForTramite(tramiteId);
        ObjectListing objectListing = amazonS3Client.listObjects(bucketName);
        List<DocumentoDTO> documentos = new ArrayList<>();

        while (true) {
            List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
            if (objectSummaries.isEmpty()) {
                break;
            }

            for (S3ObjectSummary summary : objectSummaries) {
                DocumentoDTO documentoDTO = new DocumentoDTO();
                documentoDTO.setName(summary.getKey());

                documentos.add(documentoDTO);
            }

            objectListing = amazonS3Client.listNextBatchOfObjects(objectListing);
        }

        log.info("Files found in bucket({}): {}", bucketName, documentos);

        return documentos;
    }

    public void uploadFile(String filename, MultipartFile file, Long tramiteId) throws Exception {
        String extension = FilenameUtils.getExtension(filename);
        if (extension == null || extension.isEmpty()) {
            throw new IllegalArgumentException("El archivo no tiene una extensión válida.");
        }

        String bucketName = getOrCreateBucketForTramite(tramiteId);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        amazonS3Client.putObject(bucketName, filename, file.getInputStream(), metadata);
        log.info("Archivo subido al bucket({}): {}", bucketName, filename);
    }

    public void deleteFile(String filename, Long tramiteId) {
        String bucketName = getOrCreateBucketForTramite(tramiteId);

        try {
            amazonS3Client.deleteObject(bucketName, filename);
            log.info("Archivo eliminado: " + filename + " del bucket: " + bucketName);
        } catch (AmazonClientException e) {
            log.error("Error al eliminar el archivo " + filename + " del bucket " + bucketName, e);
            throw new RuntimeException("No se pudo eliminar el archivo", e);
        }
    }
}
