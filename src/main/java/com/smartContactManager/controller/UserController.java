package com.smartContactManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartContactManager.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

  Logger logger=LoggerFactory.getLogger(UserController.class);  


  

  
  @Autowired
  private UserService userService;


    @GetMapping("/dashboard")
    public String userDashboard(){
      return "user/dashboard";
    }

    @GetMapping("/profile")
    public String userProfile(Model model,Authentication authentication){
      return "user/profile";
    }

}
