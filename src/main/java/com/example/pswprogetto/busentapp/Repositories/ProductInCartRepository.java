package com.example.pswprogetto.busentapp.Repositories;

import com.example.pswprogetto.busentapp.Entities.Cart;
import com.example.pswprogetto.busentapp.Entities.Product;
import com.example.pswprogetto.busentapp.Entities.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInCartRepository extends JpaRepository<ProductInCart,Long> {

    List<ProductInCart> findByCart(Cart c);
    boolean existsByCart(Cart c);
    ProductInCart findByProductAndCart(Product p, Cart c);
    boolean existsByProductAndCart(Product p, Cart c);
    List<ProductInCart> findByProduct(Product p);
    boolean existsByProduct(Product p);
}
