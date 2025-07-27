package com.wenhao.minioapi.dto;


import jakarta.validation.constraints.NotBlank;

public class FileDownloadRequest {

    @NotBlank(message = "File ID must not be blank")
    private String fileId;

    public FileDownloadRequest() {
    }

    public FileDownloadRequest(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
