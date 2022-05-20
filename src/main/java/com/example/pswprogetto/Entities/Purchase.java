package com.example.pswprogetto.Entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="purchases", schema = "purchases")
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long Id;

    @Column(name = "dataacquisto", nullable = false)
    private Date data;

    @OneToMany
    private List<ProductInPurchase> productInPurchases;

}
