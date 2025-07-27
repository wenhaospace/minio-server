package com.wenhao.minioapi.repository;


import com.wenhao.minioapi.entity.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileMetadataRepository extends JpaRepository<FileMetadata, String> {
    // Custom query methods can be defined here if needed
    Optional<FileMetadata> findByFileId(String fileId);
}
