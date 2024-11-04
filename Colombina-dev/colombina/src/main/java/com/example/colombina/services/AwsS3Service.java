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
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.colombina.DTOs.DocumentoDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AwsS3Service {

    @Autowired
    private AmazonS3 amazonS3Client;

    private final String baseBucketName = "archivos-tramites-regulatorios";

    public String getOrCreateBucketForTramite(Long tramiteId) {
        String prefix = "tramite-" + tramiteId + "/";

        try {
            // Verificar si el bucket base existe, si no, crearlo
            if (!amazonS3Client.doesBucketExistV2(baseBucketName)) {
                amazonS3Client.createBucket(baseBucketName);
                log.info("Bucket base creado: " + baseBucketName);
            } else {
                log.info("Bucket base ya existe: " + baseBucketName);
            }

            // Retornar el prefijo que se usará para el trámite específico dentro del bucket
            // base
            return prefix;
        } catch (Exception e) {
            log.error("Error al verificar o crear el bucket base para el trámite " + tramiteId, e);
            throw new RuntimeException("No se pudo crear o verificar el bucket base", e);
        }
    }

    public InputStream getObject(String filename, Long tramiteId) {
        String prefix = getOrCreateBucketForTramite(tramiteId);
        String fullPath = prefix + filename;
    
        try {
            S3Object s3Object = amazonS3Client.getObject(baseBucketName, fullPath);
            return s3Object.getObjectContent();
        } catch (AmazonClientException e) {
            log.error("Error al obtener el archivo " + filename + " del bucket " + baseBucketName, e);
            throw new RuntimeException("No se pudo obtener el archivo", e);
        }
    }

    public List<DocumentoDTO> listFiles(Long tramiteId) throws AmazonClientException {
        String prefix = getOrCreateBucketForTramite(tramiteId);
        List<DocumentoDTO> documentos = new ArrayList<>();

        try {
            ListObjectsV2Request request = new ListObjectsV2Request()
                    .withBucketName(baseBucketName)
                    .withPrefix(prefix);

            ListObjectsV2Result result;
            do {
                result = amazonS3Client.listObjectsV2(request);
                for (S3ObjectSummary summary : result.getObjectSummaries()) {
                    DocumentoDTO documentoDTO = new DocumentoDTO();
                    documentoDTO.setName(summary.getKey().replace(prefix, ""));
                    documentos.add(documentoDTO);
                }
                request.setContinuationToken(result.getNextContinuationToken());
            } while (result.isTruncated());

            log.info("Files found in bucket({}): {}", baseBucketName, documentos);
        } catch (AmazonClientException e) {
            log.error("Error al listar archivos para el trámite " + tramiteId, e);
            throw e;
        }

        return documentos;
    }

    public void uploadFile(String filename, MultipartFile file, Long tramiteId) throws Exception {
        String extension = FilenameUtils.getExtension(filename);
        if (extension == null || extension.isEmpty()) {
            throw new IllegalArgumentException("El archivo no tiene una extensión válida.");
        }

        String prefix = getOrCreateBucketForTramite(tramiteId);
        String fullPath = prefix + filename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        file.getInputStream().mark((int) file.getSize() + 1);

        PutObjectRequest request = new PutObjectRequest(baseBucketName, fullPath, file.getInputStream(), metadata);
        request.getRequestClientOptions().setReadLimit((int) file.getSize() + 1000);

        amazonS3Client.putObject(request);
        log.info("Archivo subido al bucket({}): {}", baseBucketName, fullPath);
    }

    public void deleteFile(String filename, Long tramiteId) {
        String prefix = getOrCreateBucketForTramite(tramiteId);
        String fullPath = prefix + filename;

        try {
            amazonS3Client.deleteObject(baseBucketName, fullPath);
            log.info("Archivo eliminado: " + filename + " del bucket: " + baseBucketName);
        } catch (AmazonClientException e) {
            log.error("Error al eliminar el archivo " + filename + " del bucket " + baseBucketName, e);
            throw new RuntimeException("No se pudo eliminar el archivo", e);
        }
    }
}
