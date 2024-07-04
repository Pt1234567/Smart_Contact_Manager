package com.smartContactManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smartContactManager.repositories.UserRepo;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService{

    //user ko load karana hai database taaki ham database base wale user se login kara pae
    
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //NOTE:- email hamara user name hai

        return userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

}
