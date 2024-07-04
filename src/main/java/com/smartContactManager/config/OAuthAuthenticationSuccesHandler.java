package com.smartContactManager.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

               DefaultOAuth2User user=(DefaultOAuth2User)authentication.getPrincipal();

               logger.info(user.getName());

               user.getAttributes().forEach((k,v)->{
                     logger.info("{}=>{}",k,v);
               });

               logger.info(user.getAuthorities().toString());


               String email=user.getAttribute("email").toString();
               String name=user.getAttribute("name").toString();
               String picture=user.getAttribute("picture").toString();

               //create user
               //save in database
               User user1=new User();
               user1.setEmail(email);
               user1.setName(name);
               user1.setPassword("password");
               user1.setProfilePic(picture);
               user1.setUserId(UUID.randomUUID().toString());
               user1.setProviders(Providers.GOOGLE);
               user1.setEnabled(true);
               user1.setEmailVerified(true);
               user1.setProviderUserId(user.getName());
               user1.setRoleList(List.of(AppConstants.ROLE_USER));
               user1.setAbout("This user is created using google");

               User dbUser=userRepo.findByEmail(email).orElse(null);
               if(dbUser==null){
                     userRepo.save(user1);      
                     logger.info("User saved in database"+email);
               }   


             new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");

    }

}
