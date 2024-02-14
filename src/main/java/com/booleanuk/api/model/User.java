package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String phone;

    @Column
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = {"id", "user"})
    public List<VideoGame> videoGames;

    public User(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
