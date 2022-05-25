package com.example.pswprogetto.Entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "carts", schema = "cart")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "utente",nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<ProductInCart> productInCart;

    @Column(name = "orderprice")
    private double totalPrice;
}
