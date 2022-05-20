package com.example.pswprogetto.Controllers;

import com.example.pswprogetto.Entities.Product;
import com.example.pswprogetto.Exceptions.ProductAlreadyExistException;
import com.example.pswprogetto.Services.ProductService;
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
@RequestMapping(path="/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    //create
    @PostMapping
    public ResponseEntity createProduct(String name, String code, double price){
        Product p= new Product();
        p.setCode(code);
        p.setName(name);
        p.setPrice(price);
        try {
            productService.addProduct(p);
        } catch (ProductAlreadyExistException e) {
            return new ResponseEntity(new ResponseMessage("Prodotto esistente!"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new ResponseMessage("Prodotto inserito!"), HttpStatus.OK);
    }

    @GetMapping
    public List<Product> getAll(){
        return productService.getAll();
    }
}
