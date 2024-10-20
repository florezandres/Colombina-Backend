package com.example.colombina.services;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MinioService {
    private final MinioClient minioClient;

    @Autowired
    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }
    public InputStream getObject(String filename, Long tramiteId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        InputStream stream;
        String bucketName = getOrCreateBucketForTramite(tramiteId);
        stream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .build());

        return stream;
    }

    public void uploadFile(String filename, MultipartFile file, Long tramiteId) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String bucketName = getOrCreateBucketForTramite(tramiteId);
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
    }

    public String getOrCreateBucketForTramite(Long tramiteId) {
        String bucketName = "tramite-" + tramiteId;

        try {
            // Verificar si el bucket ya existe
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());

            if (!bucketExists) {
                // Crear el bucket si no existe
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("Bucket creado: " + bucketName);
            } else {
                log.info("Bucket ya existe: " + bucketName);
            }

            // Devolver nombre del bucket
            return bucketName;
        } catch (Exception e) {
            log.error("Error al verificar o crear el bucket para el trámite " + tramiteId, e);
            throw new RuntimeException("No se pudo crear o verificar el bucket", e);
        }
    }

    public List<String> listFiles(Long tramiteId) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String bucketName = getOrCreateBucketForTramite(tramiteId);
        ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
                .bucket(bucketName)
                .build();

        Iterable<Result<Item>> objects = minioClient.listObjects(listObjectsArgs);
        List<String> fileNames = new ArrayList<>();
        for (Result<Item> result : objects) {
            Item item = result.get();
            fileNames.add(item.objectName());
        }
        return fileNames;
    }

    public void deleteFile(String filename, Long tramiteId) throws IOException, NoSuchAlgorithmException, InvalidKeyException, ServerException, InsufficientDataException, ErrorResponseException, InvalidResponseException, XmlParserException, InternalException {
        String bucketName = getOrCreateBucketForTramite(tramiteId);

        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
            log.info("Archivo eliminado: " + filename + " del bucket: " + bucketName);
        } catch (Exception e) {
            log.error("Error al eliminar el archivo " + filename + " del bucket " + bucketName, e);
            throw new RuntimeException("No se pudo eliminar el archivo", e);
        }
    }
}
