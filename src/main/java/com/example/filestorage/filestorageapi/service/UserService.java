package com.example.filestorage.filestorageapi.service;

import com.example.filestorage.filestorageapi.model.User;
import com.example.filestorage.filestorageapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User saveOneUser(User newUser) {
        return userRepository.save(newUser);
    }


    public User getOneUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}