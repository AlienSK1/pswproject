package com.example.pswprogetto.Entities;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "productinpurchase", schema = "products")
public class ProductInPurchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false,updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="puchaseofproduct")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "productinpurchase")
    private Product product;

    @Basic
    @Column(name = "quantity",nullable = false)
    private int quantity;

}
