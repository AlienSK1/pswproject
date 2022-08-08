package com.example.pswprogetto.busentapp.Controllers;

import com.example.pswprogetto.busentapp.Entities.Order;
import com.example.pswprogetto.busentapp.Entities.User;
import com.example.pswprogetto.busentapp.Exceptions.CartDoesntExistException;
import com.example.pswprogetto.busentapp.Exceptions.ProductInCartDoesntExistException;
import com.example.pswprogetto.busentapp.Exceptions.QuantityUnaviableException;
import com.example.pswprogetto.busentapp.Exceptions.UserDoesntExistException;
import com.example.pswprogetto.busentapp.Services.OrderService;
import com.example.pswprogetto.busentapp.Utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/addOrder")
    public ResponseEntity addOrder(@RequestBody User u){
        try{
            orderService.addOrder(u);
            return new ResponseEntity(new ResponseMessage("Ordine aggiunto!"), HttpStatus.OK);
        } catch (UserDoesntExistException e) {
            return new ResponseEntity(new ResponseMessage("Utente non esistente!"), HttpStatus.BAD_REQUEST);
        } catch (ProductInCartDoesntExistException e) {
            return new ResponseEntity(new ResponseMessage("Prodotto non esistente più nel carrello!"), HttpStatus.BAD_REQUEST);
        } catch (QuantityUnaviableException e) {
            return new ResponseEntity(new ResponseMessage("Uno dei prodotti è stato acuistato in una quantità non disponibile!"), HttpStatus.BAD_REQUEST);
        } catch (CartDoesntExistException e) {
            return new ResponseEntity(new ResponseMessage("Carrello non esiste!"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="/getOrders")
    public List<Order> getAllOrder(@RequestParam String email) throws UserDoesntExistException{
        return orderService.getOrders(email);
    }
}
