package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @OneToMany(mappedBy = "user")
    private List<BorrowedGame> borrowedGames;


    public User(String username, String email, LocalDate dob) {
        this.username = username;
        this.email = email;
        this.dob = dob;
    }
}
