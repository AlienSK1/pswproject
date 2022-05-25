package com.example.pswprogetto.Services;

import com.example.pswprogetto.Entities.Cart;
import com.example.pswprogetto.Entities.User;
import com.example.pswprogetto.Exceptions.CartAlreadyExistException;
import com.example.pswprogetto.Exceptions.UserDoesntExistException;
import com.example.pswprogetto.Exceptions.UserEmailAlreadyExistException;
import com.example.pswprogetto.Repositories.CartRepository;
import com.example.pswprogetto.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public void registerUser(User u) throws UserEmailAlreadyExistException {
        if(userRepository.existsByEmail(u.getEmail()))
            throw new UserEmailAlreadyExistException();
        userRepository.save(u);
        Cart c = new Cart();
        c.setUser(u);
        cartRepository.save(c);
    }

    @Transactional
    public User getByEmail(String email) throws UserDoesntExistException {
        if(!userRepository.existsByEmail(email)){
            throw new UserDoesntExistException();
        }
        return userRepository.findByEmail(email);
    }

    @Transactional
    public List<User> getAllUsers(){ return userRepository.findAll();}
}
