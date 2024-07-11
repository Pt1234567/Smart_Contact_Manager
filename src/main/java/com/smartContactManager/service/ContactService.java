package com.smartContactManager.service;

import java.util.List;

import com.smartContactManager.entities.Contact;

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

}
