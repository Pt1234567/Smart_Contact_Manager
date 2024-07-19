package com.smartContactManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartContactManager.entities.User;
import com.smartContactManager.helpers.Message;
import com.smartContactManager.helpers.MessageType;
import com.smartContactManager.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

      @Autowired
      private UserService userService;

      @GetMapping("/verify-email")
      public String verifyEmail(@RequestParam("token")String token,HttpSession session){

            User user=userService.getUserByEmailToken(token);


            if(user!=null){
                  

                  if(user.getEmailToken().equals(token)){
                        user.setEnabled(true);
                        user.setEmailVerified(true);
                        userService.updateUser(user);
                        session.setAttribute("message", Message.builder().content("Your email is verified. Now you can login").type(MessageType.green).build());
                        return "success_page";
                  }
                  session.setAttribute("message", Message.builder().content("Something went wrong, email not verified..").type(MessageType.red).build());
                  return "error_page";
            }

            session.setAttribute("message", Message.builder().content("Something went wrong, email not verified..").type(MessageType.red).build());
            return "error_page";
      }

}
