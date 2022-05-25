package com.example.pswprogetto.Entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "productincart",schema = "cart")
public class ProductInCart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
