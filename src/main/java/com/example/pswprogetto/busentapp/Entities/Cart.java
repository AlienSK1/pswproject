package com.example.pswprogetto.busentapp.Entities;

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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE)
    private List<ProductInCart> productInCart;

    @Column(name = "totalPrice")
    private double totalPrice;
}
