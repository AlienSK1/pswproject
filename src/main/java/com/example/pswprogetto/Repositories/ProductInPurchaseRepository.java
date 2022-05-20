package com.example.pswprogetto.Repositories;

import com.example.pswprogetto.Entities.Product;
import com.example.pswprogetto.Entities.ProductInPurchase;
import com.example.pswprogetto.Entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInPurchaseRepository extends JpaRepository<ProductInPurchase, Long> {

    List<Product> findByPurchase(Purchase p);
    Purchase findByProduct(Product p);

    boolean existsByPurchase(Product p);


}
