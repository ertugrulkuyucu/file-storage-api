package com.example.filestorage.filestorageapi.repository;

import com.example.filestorage.filestorageapi.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    File findByName(String name);

}
