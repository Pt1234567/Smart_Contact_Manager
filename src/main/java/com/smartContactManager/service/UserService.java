package com.smartContactManager.service;

import java.util.List;
import java.util.Optional;

import com.smartContactManager.entities.User;


public interface UserService {
    
     User saveUser(User user);
     Optional<User> getUserById(String id);
     void deleteUserById(String id);
     Optional<User> updateUser(User user);
     List<User> getAllUsers();
     boolean isUserExistByEmail(String email);
     User getUserByEmail(String email);

}
