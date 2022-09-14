package com.example.pswprogetto.busentapp.Repositories;

import com.example.pswprogetto.busentapp.Entities.Order;
import com.example.pswprogetto.busentapp.Entities.Product;
import com.example.pswprogetto.busentapp.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByUtente(User s);
    boolean existsByUtente(User s);

    @Query("select o from Order o where o.utente=?1 and ( o.date<=?3 or o.date>=?2) ")
    List<Order> findByUserInPeriod(User u, Date d1, Date d2);

    @Query("select o from Order o where o.utente=?1 and ?2 in o.productordered")
    List<Order> userorderedproduct(User u, Product p);

}
