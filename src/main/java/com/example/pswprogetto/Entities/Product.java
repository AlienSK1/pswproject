package com.example.pswprogetto.Entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "product", schema = "products")
public class Product implements Serializable {
    private static final long serialVersionUID = 6843302791607583447L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;

    @Column(name="name",nullable = false,length = 100)
    private String name;

    @Column(name = "code",nullable = false,length = 20)
    private String code;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name="quantity", nullable = false)
    private int quantity;


}
