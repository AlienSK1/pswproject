package com.example.pswprogetto.busentapp.Entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "ordini", schema= "ordine")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente")
    private User user;

    @Column(name = "data", nullable = false)
    private Date date;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> productordered;

    @Column(name = "totalprice", nullable = true)
    private double totalprice;

}
