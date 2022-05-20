package com.example.pswprogetto.Controllers;


import com.example.pswprogetto.Entities.Product;
import com.example.pswprogetto.Entities.ProductInPurchase;
import com.example.pswprogetto.Entities.Purchase;
import com.example.pswprogetto.Exceptions.ProductInPurchaseAlreadeExistException;
import com.example.pswprogetto.Services.ProductInPurchaseService;
import com.example.pswprogetto.Utils.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="productinpurchase")
public class ProductInPurchaseController {
    @Autowired
    private ProductInPurchaseService productInPurchaseService;

    @PostMapping
    public ResponseEntity createProductInPurchase(Product product, Purchase purchase , int quantity){
        ProductInPurchase p = new ProductInPurchase();
        p.setProduct(product);
        p.setPurchase(purchase);
        p.setQuantity(quantity);
        try{
            productInPurchaseService.addProductInPurchase(p);
        }
        catch (ProductInPurchaseAlreadeExistException e){
            return  new ResponseEntity( new ResponseMessage("Il prodotto è già nell'acquisto!"), HttpStatus.BAD_REQUEST);
        }
       return  new ResponseEntity(new ResponseMessage("Prodotto aggiunto all'acquisto!"), HttpStatus.OK);
    }

}
