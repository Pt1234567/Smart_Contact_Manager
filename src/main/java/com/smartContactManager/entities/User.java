package com.smartContactManager.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails{

      @Id
      private String userId;
      
      

    
    private String name;

    
    private String password;

    private String email;

    private String phoneNumber;

      private String about;
      private String profilePic;

      private boolean enabled=true;
      private boolean emailVerified=false;
      private boolean phoneVerified=false;

      //self,google ya github se login ke liye
      @Enumerated(value=EnumType.STRING)
      private Providers providers=Providers.SELF;
      private String providerUserId;

      @OneToMany(mappedBy="user" ,cascade = CascadeType.ALL,fetch=FetchType.LAZY)
      private List<Contact> contacts =new ArrayList<>();

     
 
      @ElementCollection(fetch = FetchType.EAGER)
      private List<String> roleList=new ArrayList<>();

      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
         // list of roles USER ADMIN
          Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role->new SimpleGrantedAuthority(role)).toList();
          return roles;
      }

      //for this project email is username
      @Override
      public String getUsername() {
          return this.email;
      }

      


}
