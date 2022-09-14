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
    private ProductInOrderRepository productInOrderRepository;
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
        order.setDate(new Date( System.currentTimeMillis()));
        order.setUtente(userRepository.findByEmail(user.getEmail()));
        orderRepository.save(order);
        List<ProductInOrder>  productsOrdered = new ArrayList<>();
        for(ProductInCart product: productInCartRepository.findByCart(cart)){
            Product p = productInCartRepository.findByProductInCart(product);
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
                productInCartRepository.delete(product);
                ProductInOrder productOrdered = new ProductInOrder();
                productOrdered.setProductOrdered(p);
                productOrdered.setQuantity(product.getQuantity());
                productOrdered.setOrdine(order);
                productInOrderRepository.save(productOrdered);
            }
        }
        order.setProductordered(productsOrdered);
        order.setTotalprice(cart.getTotalPrice());
        orderRepository.save(order);
        cart.setProductInCart(new ArrayList<>());
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    @Transactional
    @Transient
    public List<Order> getOrders(@NotNull String email) throws UserDoesntExistException{
        if(!userRepository.existsByEmail(email)){
            throw new UserDoesntExistException();
        }
        User user = userRepository.findByEmail(email);
        return orderRepository.findByUtente(user);
    }
}
