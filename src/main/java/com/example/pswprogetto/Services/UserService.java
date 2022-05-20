package com.example.pswprogetto.Services;

import com.example.pswprogetto.Entities.User;
import com.example.pswprogetto.Exceptions.UserEmailAlreadyExistException;
import com.example.pswprogetto.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void registerUser(User u) throws UserEmailAlreadyExistException {
        if(userRepository.existsByEmail(u.getEmail()))
            throw new UserEmailAlreadyExistException();
        userRepository.save(u);
    }

    public List<User> getAllUsers(){ return userRepository.findAll();}
}
