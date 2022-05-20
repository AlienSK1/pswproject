package com.example.pswprogetto.Entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="users", schema = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "firstname",nullable = false,length = 48)
    private String firstname;

    @Basic
    @Column(name = "secondname",nullable = false,length = 48)
    private String secondname;

    @Basic
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Basic
    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private List<Order> orders;

}
