package com.smartContactManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.smartContactManager.entities.User;
import com.smartContactManager.helpers.Helper;
import com.smartContactManager.service.UserService;

@ControllerAdvice
public class RootController {

      @Autowired
      private UserService userService;

      Logger logger=LoggerFactory.getLogger(RootController.class);

    @ModelAttribute  //@ModelAttribute ki wajah se ye method usercontroller ke har ek handler pe call ho ga
  public void addLoggedInUserInformation(Model model,Authentication authentication){             
      //authentication ki wajah se hume user ke regarding informationn mil jati hai
      //isko use karke hum user ke email,username etc. ko fetch kar sakte hain to show on dashboard


      //since ye method har ek request pe call hogi toh authentication can be null soo
      if(authentication==null) return;
      
      logger.info("Adding logged in user to model");
      String userName=Helper.getEmailOffLoggedInUser(authentication);
      logger.info("User logged in:{}"+userName);
      User user=userService.getUserByEmail(userName);
      model.addAttribute("loggedInUser",user);
      
  }

}
