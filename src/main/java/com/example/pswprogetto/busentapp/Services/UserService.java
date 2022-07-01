package com.example.pswprogetto.busentapp.Services;

import com.example.pswprogetto.busentapp.Entities.Cart;
import com.example.pswprogetto.busentapp.Entities.User;
import com.example.pswprogetto.busentapp.Exceptions.UserDoesntExistException;
import com.example.pswprogetto.busentapp.Exceptions.UserEmailAlreadyExistException;
import com.example.pswprogetto.busentapp.Repositories.CartRepository;
import com.example.pswprogetto.busentapp.Repositories.UserRepository;
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
        String email= u.getEmail();
        if(userRepository.existsByEmail(email))
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
