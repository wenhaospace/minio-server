package com.wenhao.minioapi.service;


import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class MinioService {

    private final MinioClient minioClient;
    private final String bucketName = "my-first-bucket"; // Replace with your bucket name

    @Autowired
    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String uploadFile(MultipartFile file) throws Exception {
        String fileId = UUID.randomUUID().toString();
        try (InputStream inputStream = file.getInputStream()) {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileId)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            minioClient.putObject(putObjectArgs);
        } catch (MinioException e) {
            throw new Exception("Error occurred while uploading file: " + e.getMessage());
        }
        return fileId;
    }

    public InputStream downloadFile(String fileId) throws Exception {
        try {
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileId)
                    .build();
            return minioClient.getObject(getObjectArgs);
        } catch (MinioException e) {
            throw new Exception("Error occurred while downloading file: " + e.getMessage());
        }
    }

    public void deleteFile(String fileId) throws Exception {
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileId)
                    .build();
            minioClient.removeObject(removeObjectArgs);
        } catch (MinioException e) {
            throw new Exception("Error occurred while deleting file: " + e.getMessage());
        }
    }
}
