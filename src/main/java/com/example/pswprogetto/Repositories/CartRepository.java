package com.example.pswprogetto.Repositories;

import com.example.pswprogetto.Entities.Cart;
import com.example.pswprogetto.Entities.Product;
import com.example.pswprogetto.Entities.ProductInCart;
import com.example.pswprogetto.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByUser(User u);
    boolean existsByUser(User u);
    @Query("select c.productInCart from Cart c where c.user = ?1")
    List<ProductInCart> getProductbyUser(User u);
}
