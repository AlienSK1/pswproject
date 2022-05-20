package com.example.pswprogetto.Repositories;

import com.example.pswprogetto.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepositories extends JpaRepository<Product, Long> {

    List<Product> findByName(String name);
    List<Product> findByPrice(double price);
    List<Product> findByCode(String code);
    boolean existsByName(String name);

}
