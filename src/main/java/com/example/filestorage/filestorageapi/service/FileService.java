package com.example.filestorage.filestorageapi.service;

import com.example.filestorage.filestorageapi.model.File;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    public ResponseEntity<?> uploadFile(MultipartFile file);

    public void deleteFile(File file);

    public void updateFile();

    public List<File> getAllFiles();

    public File getFileByName(String name);

    public File getFileById(Long id);

    public ResponseEntity<?> downloadFile(Long id);

}
