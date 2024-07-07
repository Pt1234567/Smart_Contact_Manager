package com.smartContactManager.config;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.smartContactManager.service.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
     
//these user and pass are in memory and not from database
//     @Bean
//     public UserDetailsService userDetailsService(){

//        UserDetails user = User
//        .withDefaultPasswordEncoder()
//        .username("abc123")
//        .password("abc123")
//        .roles("ADMIN","USER")
//        .build();

//        UserDetails user1 = User.withUsername("test123")
//        .password("test123")
//        .build();

//        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user,user1);
//        return inMemoryUserDetailsManager;
//     }

       @Autowired
       private SecurityCustomUserDetailService userDetailService;

       @Autowired
       private OAuthAuthenticationSuccesHandler handler;
      
       @Bean
       public AuthenticationProvider authenticationProvider(){

             DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            
             daoAuthenticationProvider.setUserDetailsService(userDetailService);
             daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            
             return daoAuthenticationProvider;  
       }
   
       @Bean
       public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            
             httpSecurity.authorizeHttpRequests((authorizeRequests)->{
                       authorizeRequests.requestMatchers("/user/**").authenticated();
                       authorizeRequests.anyRequest().permitAll();
             }
             );

             httpSecurity.formLogin(formLogin->{

                  formLogin.loginPage("/login");
                  formLogin.loginProcessingUrl("/authenticate");//form login hoke is url pe submit hoga
                  formLogin.defaultSuccessUrl("/user/profile"); //agar login success hua toh yeh url pe redirect hoga
                  ///formLogin.failureForwardUrl("/login?error=true"); //agar login fail hua toh yeh url pe redirect hoga  
                  formLogin.usernameParameter("email");
                  formLogin.passwordParameter("password");
                    
             });
             
             httpSecurity.csrf(AbstractHttpConfigurer::disable);
             httpSecurity.logout(logoutForm->{
                 logoutForm.logoutUrl("/logout");
             });

             //oauth 2config
                   //httpSecurity.oauth2Login(Customizer.withDefaults());
             //ham ab default oauth2 login use nahi karenge ab apne hisaab se customize karenge
             httpSecurity.oauth2Login(oauth->{
                  oauth.loginPage("/login");
                  oauth.successHandler(handler);
              });

            return httpSecurity.build();
       }


       @Bean
       public PasswordEncoder passwordEncoder(){
             return new BCryptPasswordEncoder();
       }
}
