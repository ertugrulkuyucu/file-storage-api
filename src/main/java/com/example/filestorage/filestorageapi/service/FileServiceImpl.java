package com.example.filestorage.filestorageapi.service;

import com.example.filestorage.filestorageapi.model.File;
import com.example.filestorage.filestorageapi.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class FileServiceImpl implements FileService {

    @Value("${uploads.path}")
    private String UPLOAD_DIR;
    private static List<String> ALLOWED_EXTENSIONS = Arrays.asList("png", "jpeg", "jpg", "docx", "pdf", "xlsx");
    private static long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    @Autowired
    private FileRepository fileRepository;

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file) {

        Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        String fileExtension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        uploadFileToDisc(file);

        // save file details to the database
        File fileDetails = new File();
        fileDetails.setName(file.getOriginalFilename());
        fileDetails.setExtension(fileExtension);
        fileDetails.setPath(filePath.toString());
        fileDetails.setSize(file.getSize());
        fileDetails.setSizeUnit("bytes");

        fileRepository.save(fileDetails);

        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> uploadFileToDisc (MultipartFile file) {
        try {
            folderChecker();

            // check file size
            if (file.getSize() > MAX_FILE_SIZE) {
                return new ResponseEntity<>("File size must be less than 5MB", HttpStatus.BAD_REQUEST);
            }

            // check file extension
            String fileExtension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            if (!ALLOWED_EXTENSIONS.contains(fileExtension)) {
                return new ResponseEntity<>("Invalid file type. Allowed types are: png, jpeg, jpg, docx, pdf, xlsx", HttpStatus.BAD_REQUEST);
            }

            Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(filePath, file.getBytes());

            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public void deleteFile(File file) {

        String fileName = file.getName();

        deleteFileFromDisc(fileName);

        fileRepository.delete(file);

    }

    @Override
    public void deleteFileFromDisc(String fileName){
        Path filePath = Paths.get(UPLOAD_DIR + fileName);

        try {
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateFile(MultipartFile file, Long id) {

        deleteFileFromDisc(file.getName());

        uploadFileToDisc(file);

        File fileToUpdate = fileRepository.findById(id).get();

        Path filePath = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        String fileExtension = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        fileToUpdate.setName(file.getOriginalFilename());
        fileToUpdate.setExtension(fileExtension);
        fileToUpdate.setPath(filePath.toString());
        fileToUpdate.setSize(file.getSize());
        fileToUpdate.setSizeUnit("bytes");

        fileRepository.save(fileToUpdate);







    }

    @Override
    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }


    @Override
    public File getFileById(Long id) {

        File file = fileRepository.findById(id).get();

        return file;
    }

    @Override
    public ResponseEntity<?> downloadFile(Long id) {
        try {

            Optional<File> file = fileRepository.findById(id);
            if (file.isEmpty()) {
                return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
            }
            String fileName = file.get().getName();

            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            byte[] fileBytes = Files.readAllBytes(filePath);

            return ResponseEntity.ok(fileBytes);

        } catch (IOException e) {
            return new ResponseEntity<>("File download failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public void folderChecker () {

        java.io.File folder = new java.io.File(UPLOAD_DIR);

        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (created) {
                Logger.getAnonymousLogger().info("Folder created: " + folder.getAbsolutePath());
            } else {
                Logger.getAnonymousLogger().info("Failed to create folder: " + folder.getAbsolutePath());
            }
        }
    }




}
