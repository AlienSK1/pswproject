package com.example.pswprogetto.Services;

import com.example.pswprogetto.Entities.Purchase;
import com.example.pswprogetto.Exceptions.PurchaseAlreadyExistException;
import com.example.pswprogetto.Repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Transactional
    public void addPurchase(Purchase p) throws PurchaseAlreadyExistException {
            if(purchaseRepository.existsById(p.getId()))
                throw new PurchaseAlreadyExistException();
            purchaseRepository.save(p);
    }
    @Transactional
    public List<Purchase> getPurchaseByData(Date d){
        return purchaseRepository.findByData(d);
    }
    @Transactional
    public List<Purchase> getPurchaseByPeriod(Date d1, Date d2){
        return  purchaseRepository.findByDataBetween(d1,d2);
    }

}
