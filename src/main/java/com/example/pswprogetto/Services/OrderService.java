package com.example.pswprogetto.Services;

import com.example.pswprogetto.Entities.*;
import com.example.pswprogetto.Exceptions.*;
import com.example.pswprogetto.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductInCartRepository productInCartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void addOrder(User u)throws UserDoesntExistException,QuantityUnaviableException, CartDoesntExistException, ProductInCartDoesntExistException {
        if(!userRepository.existsByEmail(u.getEmail())){
            throw new UserDoesntExistException();
        }
        if(! cartRepository.existsByUser(u)){
            throw new CartDoesntExistException();
        }
        Cart cart = cartRepository.findByUser(u);
        Order order = new Order();
        order.setDate(new Date());
        order.setUser(userRepository.findByEmail(u.getEmail()));
        order.setTotalprice(cart.getTotalPrice());
        List<ProductInCart> productInCart = cartRepository.getProductbyUser(u);
        List<Product>  productsordered = new ArrayList<>();
        for(ProductInCart product: productInCart){
            Product p = productRepository.findByCode(product.getProduct().getCode());
            if(product.getQuantity()>p.getQuantity()){
                throw new QuantityUnaviableException();
            }
            else{
                int quantity= p.getQuantity()- product.getQuantity();
                p.setQuantity(quantity);
                List<ProductInCart> products= p.getProductInCart();
                products.remove(product);
                p.setProductInCart(products);
                entityManager.refresh(p);
                productsordered.add(p);
                productInCartRepository.delete(product);
            }
        }
        order.setProductordered(productsordered);
        orderRepository.save(order);
        cart.setTotalPrice(0);
        cart.setProductInCart(new ArrayList<ProductInCart>());
        entityManager.refresh(cart);
    }

    @Transactional
    @Transient
    public List<Order> getOrders(User u){
        return orderRepository.findByUser(u);
    }
}
