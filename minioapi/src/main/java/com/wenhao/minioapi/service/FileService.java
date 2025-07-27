package com.wenhao.minioapi.service;

import com.wenhao.minioapi.dto.FileUploadResponse;
import com.wenhao.minioapi.entity.FileMetadata;

import com.wenhao.minioapi.exception.FileServiceException;
import com.wenhao.minioapi.repository.FileMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import com.wenhao.minioapi.util.FileUtil;
import com.wenhao.minioapi.dto.FileDownloadResponse;

@Service
public class FileService {

    @Autowired
    private MinioService minioService;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    public FileUploadResponse uploadFile(MultipartFile file) {
        validateFile(file);

        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        long fileSize = file.getSize();

        try {
            String fileId = minioService.uploadFile(file);
            FileMetadata fileMetadata = new FileMetadata(fileId, fileName, fileType, fileSize);
            fileMetadataRepository.save(fileMetadata);
            return new FileUploadResponse(fileId, fileName, fileType, fileSize, "File uploaded successfully");
        } catch (Exception e) {
            throw new FileServiceException("Error uploading file", e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty() || !FileUtil.isAllowedFileType(file.getContentType())) {
            throw new FileServiceException("Invalid file type or empty file");
        }
    }

    // Additional methods for downloading files and other business logic can be added here
    public FileDownloadResponse downloadFile(String fileId) {
        try {
            FileMetadata fileMetadata = fileMetadataRepository.findByFileId(fileId)
                    .orElseThrow(() -> new FileServiceException("File not found"));
            byte[] fileData = minioService.downloadFile(fileId).readAllBytes();
            return new FileDownloadResponse(fileMetadata, fileData);
        } catch (Exception e) {
            throw new FileServiceException("Error downloading file", e);
        }
    }
}
