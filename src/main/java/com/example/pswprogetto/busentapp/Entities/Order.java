package com.example.pswprogetto.busentapp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @JoinColumn(name = "utente")
    private User user;

    @Column(name = "data", nullable = false)
    private Date date;

    @OneToMany(mappedBy = "ordine",cascade = CascadeType.MERGE)
    private List<ProductInOrder> productordered;

    @Column(name = "totalprice", nullable = true)
    private double totalprice;

}
