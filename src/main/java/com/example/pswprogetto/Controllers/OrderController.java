package com.example.pswprogetto.Controllers;

import com.example.pswprogetto.Entities.Order;
import com.example.pswprogetto.Entities.Product;
import com.example.pswprogetto.Entities.User;
import com.example.pswprogetto.Exceptions.CartDoesntExistException;
import com.example.pswprogetto.Exceptions.ProductInCartDoesntExistException;
import com.example.pswprogetto.Exceptions.QuantityUnaviableException;
import com.example.pswprogetto.Exceptions.UserDoesntExistException;
import com.example.pswprogetto.Services.OrderService;
import com.example.pswprogetto.Utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/Orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity addOrder(User u){
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

    @GetMapping
    public List<Order> getAllOrder(User u){
        return orderService.getOrders(u);
    }
}
