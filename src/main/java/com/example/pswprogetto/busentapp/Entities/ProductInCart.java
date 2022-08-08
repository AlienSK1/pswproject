package com.example.pswprogetto.busentapp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@DynamicUpdate
@Table(name = "productincart",schema = "cart")
public class ProductInCart implements Serializable {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "cart")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;
    
    @Basic
    @Column(name = "quantity")
    private Integer quantity;
}
