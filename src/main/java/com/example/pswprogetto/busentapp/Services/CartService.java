package com.example.pswprogetto.busentapp.Services;

import com.example.pswprogetto.busentapp.Entities.Cart;
import com.example.pswprogetto.busentapp.Entities.Product;
import com.example.pswprogetto.busentapp.Entities.ProductInCart;
import com.example.pswprogetto.busentapp.Entities.User;
import com.example.pswprogetto.busentapp.Exceptions.CartDoesntExistException;
import com.example.pswprogetto.busentapp.Exceptions.ProductDoesntExistException;
import com.example.pswprogetto.busentapp.Exceptions.QuantityUnaviableException;
import com.example.pswprogetto.busentapp.Exceptions.UserDoesntExistException;
import com.example.pswprogetto.busentapp.Repositories.CartRepository;
import com.example.pswprogetto.busentapp.Repositories.ProductInCartRepository;
import com.example.pswprogetto.busentapp.Repositories.ProductRepository;
import com.example.pswprogetto.busentapp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Transactional
    public void addProductToCart(String productCode, String userEmail, int quantity) throws CartDoesntExistException, QuantityUnaviableException, ProductDoesntExistException {
        if(! productRepository.existsByCode(productCode)){
            throw new ProductDoesntExistException();
        }
        if(! userRepository.existsByEmail(userEmail)){
            throw  new CartDoesntExistException();
        }
        Product p = productRepository.findByCode(productCode);
        User u = userRepository.findByEmail(userEmail);
        Cart c = cartRepository.findByUser(u);
        if(! productInCartRepository.existsByProductAndCart(p,c)){
            if(p.getQuantity()< quantity){
                throw new QuantityUnaviableException();
            }
            ProductInCart pic = new ProductInCart();
            pic.setProduct(p);
            pic.setQuantity(quantity);
            pic.setCart(c);
            productInCartRepository.save(pic);
            List<ProductInCart> productInCart= c.getProductInCart();
            productInCart.add(pic);
            c.setTotalPrice(c.getTotalPrice()+quantity*p.getPrice());
            c.setProductInCart(productInCart);
        }
        else{
            ProductInCart pic= productInCartRepository.findByProductAndCart(p,c);
            if(p.getQuantity()< quantity+ pic.getQuantity()){
                throw new QuantityUnaviableException();
            }
            List<ProductInCart> productInCart = cartRepository.getProductbyUser(u);
            productInCart.remove(pic);
            pic.setQuantity(pic.getQuantity()+quantity);
            productInCartRepository.save(pic);
            productInCart.add(pic);
            c.setTotalPrice(c.getTotalPrice()+quantity*p.getPrice());
            c.setProductInCart(productInCart);
        }
        cartRepository.save(c);
    }
    @Transactional(readOnly = true)
    public List<ProductInCart> getProductincart( String email) throws CartDoesntExistException, UserDoesntExistException {
        if(!userRepository.existsByEmail(email)){
            throw new UserDoesntExistException();
        }
        User u = userRepository.findByEmail(email);
        if(!cartRepository.existsByUser(u)){
            throw new CartDoesntExistException();
        }

        List<ProductInCart> ris = cartRepository.getProductbyUser(u);
        return ris;
    }

    @Transactional(readOnly = true)
    public double getTotalPrice( String email) throws CartDoesntExistException, UserDoesntExistException {
        if(!userRepository.existsByEmail(email)){
            throw new UserDoesntExistException();
        }
        User u = userRepository.findByEmail(email);
        if(!cartRepository.existsByUser(u)){
            throw new CartDoesntExistException();
        }
        Cart c = cartRepository.findByUser(u);
        return c.getTotalPrice();
    }

    @Transactional(readOnly = true)
    public List<Product> getProductInCart( String email) throws CartDoesntExistException, UserDoesntExistException {
        if(!userRepository.existsByEmail(email)){
            throw new UserDoesntExistException();
        }
        User u= userRepository.findByEmail(email);
        if(!cartRepository.existsByUser(u)){
            throw new CartDoesntExistException();
        }
        List<ProductInCart> products = cartRepository.getProductbyUser(cartRepository.findByUser(u).getUser());
        List<Product> ret= new ArrayList<>();
        for(ProductInCart p: products){
            ret.add(p.getProduct());
        }
        return ret;
    }

    @Transactional(readOnly = true)
    public Cart getCart( User u) throws CartDoesntExistException {
        User user = userRepository.findByEmail(u.getEmail());
        if(!cartRepository.existsByUser(user)){
            throw new CartDoesntExistException();
        }
        return cartRepository.findByUser(user);
    }
}
