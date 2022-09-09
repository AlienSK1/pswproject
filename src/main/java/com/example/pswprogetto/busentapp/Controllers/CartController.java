package com.example.pswprogetto.busentapp.Controllers;

import com.example.pswprogetto.busentapp.Entities.Cart;
import com.example.pswprogetto.busentapp.Entities.Product;
import com.example.pswprogetto.busentapp.Entities.ProductInCart;
import com.example.pswprogetto.busentapp.Entities.User;
import com.example.pswprogetto.busentapp.Exceptions.CartDoesntExistException;
import com.example.pswprogetto.busentapp.Exceptions.ProductDoesntExistException;
import com.example.pswprogetto.busentapp.Exceptions.QuantityUnaviableException;
import com.example.pswprogetto.busentapp.Services.CartService;
import com.example.pswprogetto.busentapp.Utils.ResponseMessage;
import com.example.pswprogetto.busentapp.Exceptions.UserDoesntExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cart")
@CrossOrigin(origins = "*")
public class CartController {
    @Autowired
    private CartService cartService;
    @PostMapping(path = "/addProductToCart")
    public ResponseEntity addProductToCart(@RequestParam String productcode, @RequestParam String useremail, @RequestParam  String quantity){
        try{
            cartService.addProductToCart(productcode,useremail,Integer.parseInt(quantity));
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
    public ResponseEntity getProductInCart(@RequestParam String email){
        try{
            List<Product> products =cartService.getProductInCart(email);
            return  new ResponseEntity(products, HttpStatus.OK);
        } catch (CartDoesntExistException e) {
            return  new ResponseEntity(new ResponseMessage("Cart doesn't exist"), HttpStatus.BAD_REQUEST);
        } catch (UserDoesntExistException e) {
            return  new ResponseEntity(new ResponseMessage("User doesn't exist"), HttpStatus.BAD_REQUEST);
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
    @GetMapping(path = "/totalPrice")
    public ResponseEntity getTotalPrice(@RequestParam String email){
        try {
            Double total = cartService.getTotalPrice(email);
            return  new ResponseEntity(total, HttpStatus.OK);
        } catch (CartDoesntExistException e) {
            return  new ResponseEntity(new ResponseMessage("Cart doesn't exist"), HttpStatus.BAD_REQUEST);
        } catch (UserDoesntExistException e) {
            return new ResponseEntity(new ResponseMessage("User doesn't exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/products")
    public ResponseEntity getProductincart(@RequestParam String email){
        try{
            List<ProductInCart> products =cartService.getProductincart(email);
            return  new ResponseEntity(products, HttpStatus.OK);
        } catch (CartDoesntExistException e) {
            return  new ResponseEntity(new ResponseMessage("Cart doesn't exist"), HttpStatus.BAD_REQUEST);
        } catch (UserDoesntExistException e) {
            return  new ResponseEntity(new ResponseMessage("User doesn't exist"), HttpStatus.BAD_REQUEST);
        }
    }
}
