package com.smartContactManager.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartContactManager.entities.User;

@Repository
public interface UserRepo extends  JpaRepository<User,String>{
  Optional<User> findByEmail(String email);

  Optional<User> findByEmailToken(String token);
}
