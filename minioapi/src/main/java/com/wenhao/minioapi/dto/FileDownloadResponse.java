package com.wenhao.minioapi.dto;

import com.wenhao.minioapi.entity.FileMetadata;
import jakarta.persistence.Entity;

import java.io.Serializable;


public class FileDownloadResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private FileMetadata fileMetadata;
    private byte[] fileData;

    public FileDownloadResponse(FileMetadata fileMetadata, byte[] fileData) {
        this.fileMetadata = fileMetadata;
        this.fileData = fileData;
    }

    public FileMetadata getFileMetadata() {
        return fileMetadata;
    }

    public void setFileMetadata(FileMetadata fileMetadata) {
        this.fileMetadata = fileMetadata;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
