package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String username;

    @Column
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = {"user", "lends"})
    private List<Lend> lends;

    public User(int id) {
        this.id = id;
    }

    public void addLend(Lend l) {
        this.lends.add(l);
    }

}
