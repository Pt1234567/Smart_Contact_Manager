package com.smartContactManager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class Page {
    
      @RequestMapping("/hii")
      public String hii(){
            System.out.println("hii");
            return "hii";
      }

      @RequestMapping("/about")
      public String aboutPage(Model model){
            model.addAttribute("isLogin", true);
            System.out.println("About page loading");
            return "about";
      }

      @RequestMapping("/services")
      public String servicePage(){
            System.out.println("Service page loading");
            return "service";
      }

      @RequestMapping("/login")
      public String loginPage(){
            System.out.println("login page loading");
            return "login";
      }

      @RequestMapping("/register")
      public String signUpPage(){
            return "register";
      }

      @RequestMapping("/contact")
      public String contactPage(){
            System.out.println("Contact page loading");
            return "contact";
      }

      

}
