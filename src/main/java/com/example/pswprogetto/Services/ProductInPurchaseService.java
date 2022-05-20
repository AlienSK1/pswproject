package com.example.pswprogetto.Services;

import com.example.pswprogetto.Entities.Product;
import com.example.pswprogetto.Entities.ProductInPurchase;
import com.example.pswprogetto.Entities.Purchase;
import com.example.pswprogetto.Exceptions.ProductInPurchaseAlreadeExistException;
import com.example.pswprogetto.Repositories.ProductInPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInPurchaseService {
    @Autowired
    private ProductInPurchaseRepository productInPurchaseRepository;

    @Transactional
    public void addProductInPurchase(ProductInPurchase p) throws ProductInPurchaseAlreadeExistException {
        if(productInPurchaseRepository.existsById(p.getId()) || productInPurchaseRepository.existsByPurchase(p.getProduct()) )
            throw new ProductInPurchaseAlreadeExistException();
        productInPurchaseRepository.save(p);
    }
    @Transactional
    public List<ProductInPurchase> getAll(){ return productInPurchaseRepository.findAll();}
    @Transactional
    public Purchase getPurchaseByProduct(Product p){ return productInPurchaseRepository.findByProduct(p);}
    @Transactional
    public List<Product> getProductsByPurchase(Purchase p){return  productInPurchaseRepository.findByPurchase(p);}
}
