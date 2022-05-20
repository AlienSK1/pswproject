package com.example.pswprogetto.Repositories;

import com.example.pswprogetto.Entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase,Long> {

    List<Purchase> findByData(Date d);
    List<Purchase> findByDataBetween(Date d1, Date d2);
}
