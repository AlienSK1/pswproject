package com.example.pswprogetto.Repositories;

import com.example.pswprogetto.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);
    List<User> findByFirstnameAndSecondname(String firstname, String secondname);
    List<User> findByFirstname(String firstname);
    List<User> findBySecondname(String secondname);
    boolean existsByEmail(String email);
}
