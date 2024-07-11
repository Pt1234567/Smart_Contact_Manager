package com.smartContactManager.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartContactManager.entities.Contact;
import com.smartContactManager.helpers.ResourceNotFoundException;
import com.smartContactManager.repositories.ContactRepo;
import com.smartContactManager.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

     @Autowired
     private ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        String contactId=UUID.randomUUID().toString();
        contact.setContactId(contactId);
        return  contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
      throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Contact> getAll() {
      return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
       return contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User does not exist"));
    }

    @Override
    public void delete(String id) {
        Contact contact=contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User does not exist"));
        contactRepo.delete(contact);
    }

    @Override
    public List<Contact> search(String name, String email, String number) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Contact> getByUserId(String userId) {
       return contactRepo.findByUserId(userId);
    }

}
