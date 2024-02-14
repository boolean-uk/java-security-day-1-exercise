package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String name;

    @Column(name = "game_studio")
    private String email;

    @Column(name = "genre")
    private String phone;

    @OneToMany(mappedBy = "borrowedBy")
    @JsonIgnoreProperties(value = "borrowedBy", allowSetters = true)
    private List<Game> currentlyBorrowedGames;

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public User(int id){ this.id = id;}

}
