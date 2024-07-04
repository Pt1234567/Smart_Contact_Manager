package com.smartContactManager.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smartContactManager.entities.User;
import com.smartContactManager.helpers.AppConstants;
import com.smartContactManager.helpers.ResourceNotFoundException;
import com.smartContactManager.repositories.UserRepo;
import com.smartContactManager.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
       String id=UUID.randomUUID().toString();
        user.setUserId(id);

        //encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoleList(List.of(AppConstants.ROLE_USER));
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public void deleteUserById(String id) {
        userRepo.deleteById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
       User dbUser=userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found with id "+user.getUserId()));
       //update database user
       dbUser.setName(user.getName());
      dbUser.setEmail(user.getEmail());
      dbUser.setPassword(user.getPassword());
      dbUser.setAbout(user.getAbout());
      dbUser.setPhoneNumber(user.getPhoneNumber());
      dbUser.setProfilePic(user.getProfilePic());
      dbUser.setEmailVerified(user.isEmailVerified());
      dbUser.setPhoneVerified(user.isPhoneVerified());
      dbUser.setEnabled(user.isEnabled());
      dbUser.setProviders(user.getProviders());
      dbUser.setProviderUserId(user.getProviderUserId());

      //save the user
      User save=userRepo.save(dbUser);
      return Optional.ofNullable(save);
    }

    @Override
    public List<User> getAllUsers() {
       List<User> users=userRepo.findAll();
       return users;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User dbUser=userRepo.findByEmail(email).orElse(null);
        return dbUser!=null;
    }

}
