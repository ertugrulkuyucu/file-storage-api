package com.example.filestorage.filestorageapi.dto;

import lombok.Data;

@Data
public class FileDto {

    private Long id;
    private String name;
    private String extension;
    private String path;
    private Long size;

}
