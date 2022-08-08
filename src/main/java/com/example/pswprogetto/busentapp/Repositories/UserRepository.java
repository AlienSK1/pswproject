package com.example.pswprogetto.busentapp.Repositories;

import com.example.pswprogetto.busentapp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findByFirstnameAndLastname(String firstname, String lastname);
    List<User> findByFirstname(String firstname);
    List<User> findByLastname(String lastname);
    boolean existsByEmail(String email);
}
