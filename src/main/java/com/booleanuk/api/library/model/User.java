package com.booleanuk.api.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email ;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = {"users"})
    private List<Loan> loans;



    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(int id) {
        this.id = id;
    }
}
