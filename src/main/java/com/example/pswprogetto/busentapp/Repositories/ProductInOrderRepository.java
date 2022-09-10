package com.example.pswprogetto.busentapp.Repositories;

import com.example.pswprogetto.busentapp.Entities.Order;
import com.example.pswprogetto.busentapp.Entities.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInOrderRepository extends JpaRepository<ProductInOrder,Long> {
    List<ProductInOrder> findByOrdine(Order ordine);
    boolean existsByOrdine(Order ordine);
}
