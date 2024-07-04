package com.smartContactManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.smartContactManager.entities.User;
import com.smartContactManager.forms.UserForm;
import com.smartContactManager.helpers.Message;
import com.smartContactManager.helpers.MessageType;
import com.smartContactManager.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class PageController {

      @Autowired
      private UserService userService;
    


      @GetMapping("/")
      public String hii(){
            System.out.println("hii");
            return "hii";
      }

      @GetMapping("/about")
      public String aboutPage(Model model){
            model.addAttribute("isLogin", true);
            System.out.println("About page loading");
            return "about";
      }

      @GetMapping("/services")
      public String servicePage(){
            System.out.println("Service page loading");
            return "service";
      }

     

      @GetMapping("/login")
      public String loginPage(){
            System.out.println("login page loading");
            return "login";
      }

      @GetMapping("/register")
      public String signUpPage(Model model){
            UserForm userForm = new UserForm();
            model.addAttribute("userForm", userForm);
            return "register";
      }

      @GetMapping("/contact")
      public String contactPage(){
            System.out.println("Contact page loading");
            return "contact";
      }


      //processing registration
      @PostMapping("/do-register")
      public String doRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult,HttpSession session){
            //fetch form data
            System.out.println(userForm);
            //save to database
            //userform-->user
            if(rBindingResult.hasErrors()){
                  return "register";
            }
            User user=new User();
            
            user.setName(userForm.getName());
            user.setEmail(userForm.getEmail());
            user.setPassword(userForm.getPassword());
            user.setPhoneNumber(userForm.getPhoneNumber());
            user.setAbout(userForm.getAbout());

           User savedUser= userService.saveUser(user);

           Message message= Message.builder().content("Registration Successful").type(MessageType.blue).build();

           session.setAttribute("message",message);
            
            return "redirect:/register";
      }

      

}
