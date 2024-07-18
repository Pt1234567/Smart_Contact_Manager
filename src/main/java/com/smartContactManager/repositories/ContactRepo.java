package com.smartContactManager.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartContactManager.entities.Contact;
import com.smartContactManager.entities.User;


@Repository
public interface  ContactRepo extends JpaRepository<Contact,String> {

      Page<Contact> findByUser(User user,Pageable pageable);

      @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
      List<Contact> findByUserId(@Param("userId") String userId);

    Page<Contact> findByUserAndContactNameContaining(User user,String contactName, Pageable pageable);
    Page<Contact> findByUserAndContactEmailContaining(User user,String contactEmail, Pageable pageable);
    Page<Contact> findByUserAndContactPhoneContaining(User user,String contactPhone, Pageable pageable);
    
}
