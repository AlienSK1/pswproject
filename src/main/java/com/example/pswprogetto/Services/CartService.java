package com.example.pswprogetto.Services;

import com.example.pswprogetto.Entities.Cart;
import com.example.pswprogetto.Entities.Product;
import com.example.pswprogetto.Entities.ProductInCart;
import com.example.pswprogetto.Entities.User;
import com.example.pswprogetto.Exceptions.*;
import com.example.pswprogetto.Repositories.CartRepository;
import com.example.pswprogetto.Repositories.ProductInCartRepository;
import com.example.pswprogetto.Repositories.ProductRepository;
import com.example.pswprogetto.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductInCartRepository productInCartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void addProductToCart(Cart c, Product p, int q) throws ProductDoesntExistException,QuantityUnaviableException, CartDoesntExistException{
        if(! productRepository.existsByCode(p.getCode())){
            throw new ProductDoesntExistException();
        }
        if(! userRepository.existsByEmail(c.getUser().getEmail()) || cartRepository.existsByUser(c.getUser())){
            throw new CartDoesntExistException();
        }
        //aggiunta al carrello di un prodotto già esistente
        if( productInCartRepository.existsByProductAndCart(p,c)){
            ProductInCart pic = productInCartRepository.findByProductAndCart(p,c);
            int quantity= pic.getQuantity()+q;
            if(quantity > productRepository.findByCode(p.getCode()).getQuantity()){
                    throw  new QuantityUnaviableException();
            }
            pic.setQuantity(quantity);
            entityManager.refresh(pic);
            Cart cart = cartRepository.findByUser(c.getUser());
            cart.setTotalPrice(cart.getTotalPrice()+q*productRepository.findByCode(p.getCode()).getPrice());
            entityManager.refresh(cart);
        }
        //aggiunta al carrello di un nuovo prodotto
        else {
            Cart cart = cartRepository.findByUser(c.getUser());
            ProductInCart pic = new ProductInCart();
            pic.setCart(cart);
            pic.setProduct(productRepository.findByCode(p.getCode()));
            if (q > productRepository.findByCode(p.getCode()).getQuantity()) {
                throw new QuantityUnaviableException();
            } else {
                pic.setQuantity(q);
            }
            productInCartRepository.save(pic);
            List<ProductInCart> products = cart.getProductInCart();
            products.add(pic);
            cart.setProductInCart(products);
            entityManager.refresh(cart);
        }
    }

    @Transactional
    public double getTotalPrice(Cart c) throws CartDoesntExistException{
        if(!cartRepository.existsByUser(c.getUser())){
            throw new CartDoesntExistException();
        }
        return cartRepository.findByUser(c.getUser()).getTotalPrice();
    }

    @Transactional
    public List<Product> getProductInCart(Cart c) throws CartDoesntExistException{
        if(!cartRepository.existsByUser(c.getUser())){
            throw new CartDoesntExistException();
        }
        List<ProductInCart> products = cartRepository.getProductbyUser(cartRepository.findByUser(c.getUser()).getUser());
        List<Product> ret= new ArrayList<>();
        for(ProductInCart p: products){
            ret.add(p.getProduct());
        }
        return ret;
    }

    @Transactional
    public Cart getCart(User u) throws CartDoesntExistException {
        User user = userRepository.findByEmail(u.getEmail());
        if(!cartRepository.existsByUser(user)){
            throw new CartDoesntExistException();
        }
        return cartRepository.findByUser(user);
    }
}
