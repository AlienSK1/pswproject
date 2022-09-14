package com.example.pswprogetto.busentapp.Repositories;

import com.example.pswprogetto.busentapp.Entities.Product;
import com.example.pswprogetto.busentapp.Entities.ProductInCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
    @Query("select p from Product p where upper(p.name) like upper(concat('%',?1,'%'))")
    List<Product> findByNameContaining(String name);
    Product findByCode(String code);
    boolean existsByCode(String code);
}
