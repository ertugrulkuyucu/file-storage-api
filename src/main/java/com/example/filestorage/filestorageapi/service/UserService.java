package com.example.filestorage.filestorageapi.service;

import com.example.filestorage.filestorageapi.model.User;

public interface UserService {

    User saveOneUser(User newUser);

    User getOneUserByUserName(String userName);
}
