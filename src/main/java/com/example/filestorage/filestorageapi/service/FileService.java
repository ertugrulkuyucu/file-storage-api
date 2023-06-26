package com.example.filestorage.filestorageapi.service;

import com.example.filestorage.filestorageapi.model.File;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface FileService {

    ResponseEntity<?> uploadFile(MultipartFile file);

    ResponseEntity<?> uploadFileToDisc(MultipartFile file);

    void deleteFile(File file);

    void deleteFileFromDisc(String fileName);

    void updateFile(MultipartFile file, Long id);

    List<File> getAllFiles();

    File getFileById(Long id);

    ResponseEntity<?> downloadFile(Long id);

}
