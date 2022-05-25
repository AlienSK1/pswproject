package com.example.pswprogetto.Services;

import com.example.pswprogetto.Entities.Product;
import com.example.pswprogetto.Exceptions.ProductAlreadyExistException;
import com.example.pswprogetto.Repositories.ProductRepository;
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
    private ProductRepository productRepository;

    @Transactional
    public void addProduct(Product P) throws ProductAlreadyExistException {
        if(productRepository.existsByCode(P.getCode()))
            throw new ProductAlreadyExistException();
        productRepository.save(P);
    }

    @Transactional
    public List<Product> getALlProduct(int pageNumber, int pageSize, String orderBy){
        Pageable paging= PageRequest.of(pageNumber,pageSize, Sort.by(orderBy));
        Page<Product> page = productRepository.findAll(paging);
        if(page.hasContent()){
            return page.toList();
        }
        return new ArrayList<Product>();

    }

    @Transactional
    public List<Product> getAll(){
        return productRepository.findAll();
    }

    @Transactional
    public List<Product> getProductByName(String name){
        return productRepository.findByName(name);
    }

    @Transactional
    public Product getProductByCode(String code){
        return productRepository.findByCode(code);
    }
}
