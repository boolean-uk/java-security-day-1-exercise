package com.booleanuk.api.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String phone;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Loan> loans;

    public User(String name, String phone){
        this.name = name;
        this.phone = phone;
    }
}
