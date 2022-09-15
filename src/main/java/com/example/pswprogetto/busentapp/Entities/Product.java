package com.example.pswprogetto.busentapp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "products", schema = "product")
public class Product implements Serializable{
    @Id
    @ToString.Exclude
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name="name",nullable = false,length = 100)
    private String name;
    @Column(name = "bar_code",nullable = false,length = 20)
    private String code;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name="quantity", nullable = false)
    private int quantity;
    @Column(name="image", nullable = false)
    private String image;
    @OneToMany(targetEntity = ProductInCart.class, mappedBy = "product", cascade = CascadeType.MERGE)
    @ToString.Exclude
    @JsonIgnore
    private List<ProductInCart> productInCart;
    @OneToMany(targetEntity = ProductInOrder.class,mappedBy = "productOrdered", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<ProductInOrder> productInOrder;
}
