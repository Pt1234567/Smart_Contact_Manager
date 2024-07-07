package com.smartContactManager.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.smartContactManager.controller.UserController;


public class Helper {

   public static String getEmailOffLoggedInUser(Authentication authentication){

      Logger logger=LoggerFactory.getLogger(UserController.class); 
      
         //agr user emai and password se login hai toh kaise email nikalenge
         

      // Check the type of authentication
      if (authentication instanceof OAuth2AuthenticationToken) {
             
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
             String userName="";
             var oauth2User=(OAuth2User)authentication.getPrincipal();

            if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                //sign in with google ho skta hai
                logger.info(" Google sign in");
                userName=oauth2User.getAttribute("email").toString();
            }else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                //sign in with github ho skta hai
                logger.info("Github sign in");
                userName=oauth2User.getAttribute("email")!=null?oauth2User.getAttribute("email").toString():oauth2User.getAttribute("login").toString()+"@gmail.com";
             }
            return userName;
         }else{
            //sign in with email and password hai
            System.out.println("Email and Password from local database");
            return authentication.getName();
         }

   }
}
