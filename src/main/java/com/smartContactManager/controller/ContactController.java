package com.smartContactManager.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smartContactManager.entities.Contact;
import com.smartContactManager.entities.User;
import com.smartContactManager.forms.ContactForm;
import com.smartContactManager.forms.ContactSearchForm;
import com.smartContactManager.helpers.AppConstants;
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
            
            logger.info("File infromation" ,contactForm.getContactImage().getOriginalFilename());
            

            Contact contact=new Contact();
            contact.setContactName(contactForm.getName());
            contact.setContactEmail(contactForm.getEmail());
            contact.setContactPhone(contactForm.getPhoneNumber());
            contact.setAddress(contactForm.getAddress());
            contact.setFavourite(contactForm.isFavourite());
            contact.setGithubLink(contactForm.getGithubLink());
            contact.setLinkedInlink(contactForm.getLinkedLink());
            contact.setUser(user);

            if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()) {

                  String fileName=UUID.randomUUID().toString();
                  String url=imageService.uploadImage(contactForm.getContactImage(), fileName);
                  contact.setCloudinaryPublicId(fileName);
                  contact.setContactPicture(url);
            }

             contactService.save(contact);

            session.setAttribute("message",Message.builder()
            .content("Contact saved successfully")
            .type(MessageType.green)
            .build());
            return "redirect:/user/contacts/add";
      }
    
      @RequestMapping
      public String viewContacts(Authentication authentication,
      @RequestParam(value="page",defaultValue="0")int page,
      @RequestParam(value="size",defaultValue="10")int size,
      @RequestParam(value="sortBy" ,defaultValue="name")String sortBy,
      @RequestParam(value="direction", defaultValue="asc")String direction,
      Model model){
            //load all contacts
            //get all contacts for logged in user

            String userName=Helper.getEmailOffLoggedInUser(authentication);
            User user=userService.getUserByEmail(userName);

            Page<Contact> contacts = contactService.getByUser(user,page,size,sortBy,direction);
            model.addAttribute("contacts", contacts);
            model.addAttribute("contactSearchForm",new ContactSearchForm());
            model.addAttribute("pageSize",AppConstants.PAGE_SIZE);

            return "user/contacts";
      }


      //searhc handler
      @GetMapping("/search")
      public String searchContacts(
      @ModelAttribute ContactSearchForm contactSearchForm,
      @RequestParam(value="size",defaultValue=AppConstants.PAGE_SIZE+"")int size,
      @RequestParam(value="page",defaultValue="0")int page,
      @RequestParam(value="sortby" ,defaultValue="contactName")String sortby,
      @RequestParam(value="direction" ,defaultValue="asc")String direction,
      Model model ,
      Authentication authentication){


            logger.info("field {} keyword {} ",contactSearchForm.getField(),contactSearchForm.getValue());

            Page<Contact> contacts=null;
            User user=userService.getUserByEmail(Helper.getEmailOffLoggedInUser(authentication));

           if(contactSearchForm.getField().equalsIgnoreCase("contactName")){
            contacts=contactService.searchByname(contactSearchForm.getValue(), size, page, sortby, direction,user);
           }
           else if(contactSearchForm.getField().equalsIgnoreCase("contactEmail")){
            contacts=contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortby, direction,user);
           }else if(contactSearchForm.getField().equalsIgnoreCase("contactPhone")){
            contacts=contactService.searchByPhone(contactSearchForm.getValue(), size, page, sortby, direction,user);
           }

             model.addAttribute("contacts",contacts);
             model.addAttribute("contactSearchForm",contactSearchForm);
             logger.info("contacts {}",contacts);     
             model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
             return "user/search";

      }
        

      @RequestMapping("/delete/{id}")
      public String deleteContact(@PathVariable String id,HttpSession httpSession){
            contactService.delete(id);  

           httpSession.setAttribute("message",Message.builder().content("Contact deleted successfully").type(MessageType.green).build());

            return "redirect:/user/contacts";
      }

      @GetMapping("/view/{contactId}")
      public String viewContact(@PathVariable("contactId")String contactId,Model model){

                var contact=contactService.getById(contactId);

                ContactForm contactForm=new ContactForm();

                contactForm.setName(contact.getContactName());
                contactForm.setEmail(contact.getContactEmail());
                contactForm.setAddress(contact.getAddress());
                contactForm.setPhoneNumber(contact.getContactPhone());
                contactForm.setGithubLink(contact.getGithubLink());
                contactForm.setLinkedLink(contact.getLinkedInlink());
                contactForm.setFavourite(contact.isFavourite());
                contactForm.setPicture(contact.getContactPicture());

                model.addAttribute("contactForm",contactForm);
                model.addAttribute("contactId", contactId);

                return "user/update_contact_view";
      }

      @PostMapping("/update/{contactId}")
      public String updateContact(@PathVariable("contactId")String contactId,@Valid @ModelAttribute ContactForm contactForm,BindingResult result,Model model){
            
             if(result.hasErrors()){
                  return "user/update_contact_view";
             }

             Contact newContact=new Contact();
             newContact.setContactId(contactId);
             newContact.setContactName(contactForm.getName());
             newContact.setContactEmail(contactForm.getEmail());
             newContact.setAddress(contactForm.getAddress());
             newContact.setContactPhone(contactForm.getPhoneNumber());
             newContact.setGithubLink(contactForm.getGithubLink());
             newContact.setLinkedInlink(contactForm.getLinkedLink());
             newContact.setFavourite(contactForm.isFavourite());  
             

            if(contactForm.getContactImage()!=null && !contactForm.getContactImage().isEmpty()) {

             String fileName=UUID.randomUUID().toString();
             String url=imageService.uploadImage(contactForm.getContactImage(), fileName);
             newContact.setCloudinaryPublicId(fileName);
             newContact.setContactPicture(url);
             contactForm.setPicture(url);

            }
            logger.info(contactForm.getPicture());

            var up=contactService.update(newContact);
            model.addAttribute("message",Message.builder().content("Contact updated successfully").type(MessageType.green).build());
            return "redirect:/user/contacts/view/"+contactId;
      }
}
