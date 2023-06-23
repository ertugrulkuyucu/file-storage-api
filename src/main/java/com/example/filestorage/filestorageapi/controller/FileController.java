package com.example.filestorage.filestorageapi.controller;

import com.example.filestorage.filestorageapi.model.File;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private static String UPLOAD_DIR = "uploads/";
    private static List<String> ALLOWED_EXTENSIONS = Arrays.asList("png", "jpeg", "jpg", "docx", "pdf", "xlsx");
    private static long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB


    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // check file size
            if (file.getSize() > MAX_FILE_SIZE) {
                return new ResponseEntity<>("File size must be less than 5MB", HttpStatus.BAD_REQUEST);
            }

            // check file extension
            String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
                return new ResponseEntity<>("Invalid file type. Allowed types are: png, jpeg, jpg, docx, pdf, xlsx", HttpStatus.BAD_REQUEST);
            }

            Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            // save file details to the database
            File fileDetails = new File();
            fileDetails.setName(file.getOriginalFilename());
            fileDetails.setExtension(fileExtension);
            fileDetails.setPath(filePath.toString());
            fileDetails.setSize(file.getSize());




            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            byte[] fileBytes = Files.readAllBytes(filePath);

            // return file bytes

            return ResponseEntity.ok(fileBytes);
        } catch (IOException e) {
            return new ResponseEntity<>("File download failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}