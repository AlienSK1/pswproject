package com.example.pswprogetto.busentapp.Services;


import com.example.pswprogetto.busentapp.Entities.*;
import com.example.pswprogetto.busentapp.Exceptions.CartDoesntExistException;
import com.example.pswprogetto.busentapp.Exceptions.ProductInCartDoesntExistException;
import com.example.pswprogetto.busentapp.Exceptions.QuantityUnaviableException;
import com.example.pswprogetto.busentapp.Exceptions.UserDoesntExistException;
import com.example.pswprogetto.busentapp.Repositories.*;
import com.sun.istack.NotNull;
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
    public void addOrder(User u)throws UserDoesntExistException, QuantityUnaviableException, CartDoesntExistException, ProductInCartDoesntExistException {
        if(!userRepository.existsByEmail(u.getEmail())){
            throw new UserDoesntExistException();
        }
        User user = userRepository.findByEmail(u.getEmail());
        if(!cartRepository.existsByUser(user)){
            throw new CartDoesntExistException();
        }
        Cart cart = cartRepository.findByUser(user);
        Order order = new Order();
        order.setDate(new Date());
        order.setUser(userRepository.findByEmail(u.getEmail()));
        List<ProductInCart> productInCart = cartRepository.getProductbyUser(user);
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
                productRepository.save(p);
                productsordered.add(p);
                productInCartRepository.delete(product);
            }
        }
        order.setProductordered(productsordered);
        order.setTotalprice(cart.getTotalPrice());
        orderRepository.save(order);
        cart.setProductInCart(new ArrayList<ProductInCart>());
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    @Transactional
    @Transient
    public List<Order> getOrders(@NotNull User u) throws UserDoesntExistException{
        if(!userRepository.existsByEmail(u.getEmail())){
            throw new UserDoesntExistException();
        }
        User user = userRepository.findByEmail(u.getEmail());
        return orderRepository.findByUser(user);
    }
}
