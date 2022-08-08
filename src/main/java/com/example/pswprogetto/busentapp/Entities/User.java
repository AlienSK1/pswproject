package com.example.pswprogetto.busentapp.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@ToString
@Entity
@Table(name="utenti", schema = "utente")
public class User  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Basic
    @Column(name = "firstname",nullable = false,length = 48)
    private String firstname;

    @Basic
    @Column(name = "lastname",nullable = false,length = 48)
    private String lastname;

    @Basic
    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Basic
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Basic
    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<Order> orders;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    private Cart cart;
}
