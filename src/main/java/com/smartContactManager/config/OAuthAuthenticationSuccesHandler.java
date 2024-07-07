package com.smartContactManager.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.smartContactManager.entities.Providers;
import com.smartContactManager.entities.User;
import com.smartContactManager.helpers.AppConstants;
import com.smartContactManager.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccesHandler implements AuthenticationSuccessHandler{

     Logger logger=LoggerFactory.getLogger(OAuthAuthenticationSuccesHandler.class);

     @Autowired
     private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
             logger.info("OAuth Authentication Success Handler is called");


             //pehle find karo konsa provider login ke liye use hua hai
             var oauth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;     
             String authorizedClientRegistrationId=oauth2AuthenticationToken.getAuthorizedClientRegistrationId().toString();
             logger.info("Authorized Client Registration Id is "+authorizedClientRegistrationId);


             DefaultOAuth2User oauthUser=(DefaultOAuth2User)authentication.getPrincipal();
             //check karo kya kya attributes aaye hain for a particular provider
             oauthUser.getAttributes().forEach((k,v)->{
                  logger.info("{}=>{}",k,v);
             });

             //create user and set common attributes
             User user=new User();
             user.setUserId(UUID.randomUUID().toString());
             user.setEnabled(true);
             user.setEmailVerified(true);
             user.setRoleList(List.of(AppConstants.ROLE_USER));

             if(authorizedClientRegistrationId.equalsIgnoreCase("google")){
                  //google
                  //google attributes
                  user.setEmail(oauthUser.getAttribute("email").toString());
                  user.setName(oauthUser.getAttribute("name").toString());
                  user.setProfilePic(oauthUser.getAttribute("picture").toString());
                  user.setProviderUserId(oauthUser.getName());
                  user.setProviders(Providers.GOOGLE);
                  user.setAbout("This account is created using google");


             }else if(authorizedClientRegistrationId.equalsIgnoreCase("github")){
                    //github
                  //github attributes     

                  String email=oauthUser.getAttribute("email")!=null?oauthUser.getAttribute("email").toString():oauthUser.getAttribute("login").toString()+"@gmail.com";
                  String picture=oauthUser.getAttribute("avatar_url").toString();
                  String name=oauthUser.getAttribute("login").toString();
                  String providerUserId=oauthUser.getName();


                  user.setEmail(email);
                  user.setName(name);
                  user.setProfilePic(picture);
                  user.setProviderUserId(providerUserId);
                  user.setProviders(Providers.GITHUB);
                  user.setAbout("This account is created using github");

             }else{
                  logger.info("Unknown Provider");
             }

             //save the user

            User dbUser=userRepo.findByEmail(user.getEmail()).orElse(null);
               if(dbUser==null){
                     userRepo.save(user);      
                     logger.info("User saved in database"+user.getEmail());
               } 

            //    DefaultOAuth2User user=(DefaultOAuth2User)authentication.getPrincipal();

            //    logger.info(user.getName());

            //    user.getAttributes().forEach((k,v)->{
            //          logger.info("{}=>{}",k,v);
            //    });

            //    logger.info(user.getAuthorities().toString());


            //    String email=user.getAttribute("email").toString();
            //    String name=user.getAttribute("name").toString();
            //    String picture=user.getAttribute("picture").toString();

            //    //create user
            //    //save in database
            //    User user1=new User();
            //    user1.setEmail(email);
            //    user1.setName(name);
            //    user1.setPassword("password");
            //    user1.setProfilePic(picture);
            //    user1.setUserId(UUID.randomUUID().toString());
            //    user1.setProviders(Providers.GOOGLE);
            //    user1.setEnabled(true);
            //    user1.setEmailVerified(true);
            //    user1.setProviderUserId(user.getName());
            //    user1.setRoleList(List.of(AppConstants.ROLE_USER));
            //    user1.setAbout("This user is created using google");

            //    User dbUser=userRepo.findByEmail(email).orElse(null);
            //    if(dbUser==null){
            //          userRepo.save(user1);      
            //          logger.info("User saved in database"+email);
            //    }   


             new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

    }

}
