package com.smartContactManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartContactManager.entities.Contact;
import com.smartContactManager.service.ContactService;

@RestController
@RequestMapping("/api")
public class ApiController {

      @Autowired
      private ContactService contactService;

      @RequestMapping("/contacts/{contactId}")
      public Contact getContact(@PathVariable String contactId){
           return contactService.getById(contactId);
      }

}
