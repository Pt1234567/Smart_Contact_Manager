package com.smartContactManager.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
public class User {

      @Id
      private String userId;
      
      

    
    private String name;

    
    private String password;

    private String email;

    private String phoneNumber;

      private String about;
      private String profilePic;

      private boolean enabled=false;
      private boolean emailVerified=false;
      private boolean phoneVerified=false;

      //self,google ya github se login ke liye
      @Enumerated(value=EnumType.STRING)
      private Providers providers=Providers.SELF;
      private String providerUserId;

      @OneToMany(mappedBy="user" ,cascade = CascadeType.ALL,fetch=FetchType.LAZY)
      private List<Contact> contacts =new ArrayList<>();

      


}
