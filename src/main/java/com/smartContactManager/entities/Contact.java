package com.smartContactManager.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="contacts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {
  
      @Id
      private String  contactId;
      private String contactName;
      private String contactEmail;
      private String contactPhone;
      private String address;
      private String contactPicture;
      private String description;
      private boolean isFavourite=false;
      private String githubLink;
      private String linkedInlink;
      private String cloudinaryPublicId;

      @ManyToOne
      @JsonIgnore
      private User user;

      @OneToMany(mappedBy="contact",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
      private List<SocialLinks> socialLinks=new ArrayList<>();
}
