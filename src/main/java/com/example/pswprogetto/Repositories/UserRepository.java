package com.example.pswprogetto.Repositories;

import com.example.pswprogetto.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    List<User> findByFirstnameAndLastname(String firstname, String lastname);
    List<User> findByFirstname(String firstname);
    List<User> findByLastname(String lastname);
    boolean existsByEmail(String email);
}
