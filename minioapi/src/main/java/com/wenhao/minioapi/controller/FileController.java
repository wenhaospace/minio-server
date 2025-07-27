package com.wenhao.minioapi.controller;


import com.wenhao.minioapi.dto.FileDownloadRequest;
import com.wenhao.minioapi.dto.FileDownloadResponse;
import com.wenhao.minioapi.dto.FileUploadResponse;
import com.wenhao.minioapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*") // Allow all origins for CORS
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        FileUploadResponse response = fileService.uploadFile(file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/download")
    public ResponseEntity<FileDownloadResponse> downloadFile(@RequestBody FileDownloadRequest request) {
        FileDownloadResponse response= fileService.downloadFile(request.getFileId());
        return ResponseEntity.ok(response);
    }
}
