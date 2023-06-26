package com.example.filestorage.filestorageapi.dto;

import lombok.Data;

@Data
public class SaveFileDto {

        private String name;
        private String extension;
        private String path;
        private Long size;
}
