package com.smartContactManager.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.smartContactManager.entities.Contact;
import com.smartContactManager.entities.User;
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
        var oldContact=contactRepo.findById(contact.getContactId()).orElseThrow(()->new ResourceNotFoundException("Contact not found"));
         oldContact.setContactName(contact.getContactName());
         oldContact.setContactEmail(contact.getContactEmail());
         oldContact.setAddress(contact.getAddress());
         oldContact.setContactPhone(contact.getContactPhone());
         oldContact.setGithubLink(contact.getGithubLink());
         oldContact.setLinkedInlink(contact.getLinkedInlink());
         oldContact.setFavourite(contact.isFavourite());

         
         if(contact.getContactPicture()!=null){
            oldContact.setContactPicture(contact.getContactPicture());
            oldContact.setCloudinaryPublicId(contact.getCloudinaryPublicId());
         }
         
          
         return contactRepo.save(oldContact);
    }

    @Override
    public List<Contact> getAll() {
      return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
       return contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact does not exist"));
    }

    @Override
    public void delete(String id) {
        Contact contact=contactRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Contact does not exist"));
        contactRepo.delete(contact);
    }


    @Override
    public List<Contact> getByUserId(String userId) {
       return contactRepo.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page,int size,String sortBy,String direction) {
        
        Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        var pageable=PageRequest.of(page, size);
        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public Page<Contact> searchByname(String nameKeyword, int size, int page, String sortby, String order,User user) {
        Sort sort=order.equals("desc")?Sort.by(sortby).descending():Sort.by(sortby).ascending();
        var pageable=PageRequest.of(page,size,sort);
        return contactRepo.findByUserAndContactNameContaining(user,nameKeyword,pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortby, String order,User user) {
        Sort sort=order.equals("desc")?Sort.by(sortby).descending():Sort.by(sortby).ascending();
        var pageable=PageRequest.of(page,size,sort);
        return contactRepo.findByUserAndContactEmailContaining(user,emailKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByPhone(String phoneKeyword, int size, int page, String sortby, String order,User user) {
        Sort sort=order.equals("desc")?Sort.by(sortby).descending():Sort.by(sortby).ascending();
        var pageable=PageRequest.of(page,size,sort);
        return contactRepo.findByUserAndContactPhoneContaining(user,phoneKeyword, pageable);
    }

    

}
