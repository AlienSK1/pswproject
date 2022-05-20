package com.example.pswprogetto.Services;

import com.example.pswprogetto.Entities.Product;
import com.example.pswprogetto.Exceptions.ProductAlreadyExistException;
import com.example.pswprogetto.Repositories.ProductRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepositories productRepositories;

    @Transactional
    public void addProduct(Product P) throws ProductAlreadyExistException {
        if(productRepositories.existsById(P.getId())|| (productRepositories.findByName(P.getName()).contains(P.getName()) && productRepositories.findByCode(P.getCode()).contains(P.getCode())))
            throw new ProductAlreadyExistException();
        productRepositories.save(P);
    }

    @Transactional
    public List<Product> getALlProduct(int pageNumber, int pageSize, String orderBy){
        Pageable paging= PageRequest.of(pageNumber,pageSize, Sort.by(orderBy));
        Page<Product> page = productRepositories.findAll(paging);
        if(page.hasContent()){
            return page.toList();
        }
        return new ArrayList<Product>();

    }

    @Transactional
    public List<Product> getAll(){
        return productRepositories.findAll();
    }

    @Transactional
    public List<Product> getProductByName(String name){
        return productRepositories.findByName(name);
    }

    @Transactional
    public List<Product> getProductByCode(String code){
        return productRepositories.findByCode(code);
    }
}
