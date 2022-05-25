package com.example.pswprogetto.Controllers;

import com.example.pswprogetto.Entities.Cart;
import com.example.pswprogetto.Entities.Product;
import com.example.pswprogetto.Entities.User;
import com.example.pswprogetto.Exceptions.*;
import com.example.pswprogetto.Services.CartService;
import com.example.pswprogetto.Utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/cart")
public class CartController {
    @Autowired
    private CartService cartService;


    @PostMapping
    public ResponseEntity addProductToCart(Cart c , Product p, int quantity){
        try{
            cartService.addProductToCart(c,p,quantity);
            return new ResponseEntity(new ResponseMessage("Prodotto aggiunto al carrello!"),HttpStatus.OK);
        }
        catch (ProductDoesntExistException e) {
            return new ResponseEntity(new ResponseMessage("Il prodotto inserito non è presente nel db!"),HttpStatus.BAD_REQUEST);
        } catch (QuantityUnaviableException e) {
            return new ResponseEntity(new ResponseMessage("Non è presente la quantità richiesta del prodotto!"),HttpStatus.BAD_REQUEST);
        } catch (CartDoesntExistException e) {
            return new ResponseEntity(new ResponseMessage("Carrello non esistente"),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/productInCart")
    public ResponseEntity getProductInCart(Cart c){
        try{
            List<Product> products =cartService.getProductInCart(c);
            return  new ResponseEntity(products, HttpStatus.OK);
        } catch (CartDoesntExistException e) {
            return  new ResponseEntity(new ResponseMessage("Cart doesn't exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/getCart")
    public ResponseEntity getCart(@RequestBody User u){
        try {
            Cart c = cartService.getCart(u);
            return new ResponseEntity(c, HttpStatus.OK);
        } catch (CartDoesntExistException e) {
            return  new ResponseEntity(new ResponseMessage("Cart doesn't exist for the specified user"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity getTotalPrice(@RequestBody Cart c){
        try {
            double total = cartService.getTotalPrice(c);
            return  new ResponseEntity(total, HttpStatus.OK);
        } catch (CartDoesntExistException e) {
            return  new ResponseEntity(new ResponseMessage("Cart doesn't exist"), HttpStatus.BAD_REQUEST);
        }
    }
}
