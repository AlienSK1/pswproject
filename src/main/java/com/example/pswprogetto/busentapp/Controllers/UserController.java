package com.example.pswprogetto.busentapp.Controllers;

import com.example.pswprogetto.busentapp.Entities.User;
import com.example.pswprogetto.busentapp.Exceptions.UserDoesntExistException;
import com.example.pswprogetto.busentapp.Exceptions.UserEmailAlreadyExistException;
import com.example.pswprogetto.busentapp.Services.UserService;
import com.example.pswprogetto.busentapp.Utils.ResponseMessage;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/utenti")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping(path="/registra")
    public ResponseEntity registerUser(@RequestBody User u){
        try{
            /*System.out.println("Arrivato utente");
            KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
            KeycloakPrincipal principal=(KeycloakPrincipal)token.getPrincipal();
            KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
            AccessToken accessToken = session.getToken();
            User u = new User();
            u.setEmail(accessToken.getEmail());
            u.setFirstname(accessToken.getGivenName());
            u.setLastname(accessToken.getFamilyName());
            u.setAddress(address);
            */
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

    @GetMapping(path="/getUser")
    public ResponseEntity getByEmail(@Validated String email){
        try {
            User u = userService.getByEmail(email);
            return new ResponseEntity(u,HttpStatus.OK);
        } catch (UserDoesntExistException e) {
            return  new ResponseEntity(new ResponseMessage("Utente non esistente!"), HttpStatus.BAD_REQUEST);
        }
    }
}
