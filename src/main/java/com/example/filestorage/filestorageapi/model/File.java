package com.example.filestorage.filestorageapi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String extension;

    private String path;

    private Long size;

}
