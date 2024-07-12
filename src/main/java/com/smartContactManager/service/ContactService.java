package com.smartContactManager.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.smartContactManager.entities.Contact;
import com.smartContactManager.entities.User;

public interface  ContactService {

     //save Contacts
     Contact save(Contact Contact);

     //update Contacts
     Contact update(Contact contact);

     //get allContact
     List<Contact> getAll();

     Contact getById(String id);

     void delete(String id);

     List<Contact> search(String name,String email,String number);

     List<Contact> getByUserId(String userId);

     Page<Contact> getByUser(User user,int page ,int size,String sortField,String sortDirection);

}
