package com.example.pswprogetto.Controllers;

import com.example.pswprogetto.Entities.Cart;
import com.example.pswprogetto.Entities.User;
import com.example.pswprogetto.Exceptions.CartAlreadyExistException;
import com.example.pswprogetto.Exceptions.UserDoesntExistException;
import com.example.pswprogetto.Exceptions.UserEmailAlreadyExistException;
import com.example.pswprogetto.Services.CartService;
import com.example.pswprogetto.Services.UserService;
import com.example.pswprogetto.Utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity registerUser(@RequestBody @Validated  User u){
        try{
            userService.registerUser(u);
            return new ResponseEntity(new ResponseMessage("Utente registrato!"),HttpStatus.OK);
        }
        catch(UserEmailAlreadyExistException e){
            return  new ResponseEntity(new ResponseMessage("Email già utilizzata!"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/allUsers")
    public ResponseEntity getAll(){
        List<User> users = userService.getAllUsers();
        return  new ResponseEntity(users, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getByEmail(String email){
        try {
            User u = userService.getByEmail(email);
            return new ResponseEntity(u,HttpStatus.OK);
        } catch (UserDoesntExistException e) {
            return  new ResponseEntity(new ResponseMessage("Utente non esistente!"), HttpStatus.BAD_REQUEST);
        }
    }
}
