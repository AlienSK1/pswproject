package com.example.pswprogetto.busentapp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Data
@Entity
@DynamicUpdate
@Table(name="prodottiOrdinati",schema = "ordine")
public class ProductInOrder {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name="ordine")
    private Order ordine;
    @ManyToOne()
    @JoinColumn(name="productOrdered")
    private Product productOrdered;

    @Column(name="quantity")
    private int quantity;
}
