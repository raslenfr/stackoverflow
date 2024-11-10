package com.raslen.StackOverflow.entities;


import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name ;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

}
