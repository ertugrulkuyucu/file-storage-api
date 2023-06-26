package com.example.filestorage.filestorageapi.repository;

import com.example.filestorage.filestorageapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

}
