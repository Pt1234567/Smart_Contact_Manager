package com.smartContactManager.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartContactManager.entities.Contact;
import com.smartContactManager.entities.User;
import com.smartContactManager.forms.ContactForm;
import com.smartContactManager.helpers.Helper;
import com.smartContactManager.helpers.Message;
import com.smartContactManager.helpers.MessageType;
import com.smartContactManager.service.ContactService;
import com.smartContactManager.service.ImageService;
import com.smartContactManager.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

      private Logger logger=LoggerFactory.getLogger(ContactController.class);

      @Autowired
      private ContactService contactService;

      @Autowired
      private UserService userService;

      @Autowired
      private ImageService imageService;

      @GetMapping("/add")
      public String addContactView(Model model){
          ContactForm contactForm=new ContactForm();
          model.addAttribute("contactForm", contactForm);
          return "user/add_contact";
      }


      @PostMapping("/add")
      public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult result,Authentication authentication,HttpSession session){
            //process form data

            if(result.hasErrors()){
                  
                  result.getAllErrors().forEach((error)->logger.info(error.toString()));

                  session.setAttribute("message",Message.builder()
                  .content("Invalid details")
                  .type(MessageType.red)
                  .build());
                  return "user/add_contact";
            }

            String userName=Helper.getEmailOffLoggedInUser(authentication);
            User user=userService.getUserByEmail(userName);
            
            logger.info("File infromation" ,contactForm.getPicture().getOriginalFilename());
            String fileName=UUID.randomUUID().toString();
            String fileURL=imageService.uploadImage(contactForm.getPicture(),fileName);

            Contact contact=new Contact();
            contact.setContactName(contactForm.getName());
            contact.setContactEmail(contactForm.getEmail());
            contact.setContactPhone(contactForm.getPhoneNumber());
            contact.setAddress(contactForm.getAddress());
            contact.setFavourite(contactForm.isFavourite());
            contact.setGithubLink(contactForm.getGithubLink());
            contact.setLinkedInlink(contactForm.getLinkedLink());
            contact.setContactPicture(fileURL);
            contact.setUser(user);
            contact.setCloudinaryPublicId(fileName);

             contactService.save(contact);

            session.setAttribute("message",Message.builder()
            .content("Contact saved successfully")
            .type(MessageType.green)
            .build());
            return "redirect:/user/contacts/add";
      }
    
      @RequestMapping
      public String viewContacts(Authentication authentication,Model model){
            //load all contacts
            //get all contacts for logged in user

            String userName=Helper.getEmailOffLoggedInUser(authentication);
            User user=userService.getUserByEmail(userName);

            List<Contact> contacts = contactService.getByUserId(user.getUserId());
            model.addAttribute("contacts", contacts);

            return "user/contacts";
      }
}
