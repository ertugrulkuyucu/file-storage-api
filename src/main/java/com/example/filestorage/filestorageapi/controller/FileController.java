package com.example.filestorage.filestorageapi.controller;

import com.example.filestorage.filestorageapi.model.File;
import com.example.filestorage.filestorageapi.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {

        return fileService.uploadFile(file);

    }


    @GetMapping("/downloadFileById/{fileId}")
    public ResponseEntity<?> downloadFileById(@PathVariable Long fileId) {

            return fileService.downloadFile(fileId);

    }

    @GetMapping("/getFileInformationById/{fileId}")
    public ResponseEntity<?> getFileInformationById(@PathVariable Long fileId) {

            File file = fileService.getFileById(fileId);

            if (file == null) {
                return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(file, HttpStatus.OK);

    }

    @GetMapping("/getAllFileInformation")
    public ResponseEntity<?> getAllFileInformation() {

            List<File> files = fileService.getAllFiles();

            if (files.isEmpty()) {
                return new ResponseEntity<>("No files found", HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(files, HttpStatus.OK);

    }

    @DeleteMapping("/deleteFileById/{fileId}")
    public ResponseEntity<?> deleteFileById(@PathVariable Long fileId) {

            File file = fileService.getFileById(fileId);

            if (file == null) {
                return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
            }

            fileService.deleteFile(file);

            return new ResponseEntity<>("File deleted successfully", HttpStatus.OK);

    }

    @PutMapping("/updateFileById/{fileId}")
    public ResponseEntity<?> updateFileById(@PathVariable Long fileId, @RequestParam("file") MultipartFile file) {

            File fileToUpdate = fileService.getFileById(fileId);

            if (fileToUpdate == null) {
                return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
            }

            fileService.updateFile(file, fileId);

            return new ResponseEntity<>("File updated successfully", HttpStatus.OK);

    }

}