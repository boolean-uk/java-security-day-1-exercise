package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "year")
    private int year;

    @Column(name = "genre")
    private String genre;

    @OneToMany(mappedBy = "game")
    private List<Loan> loans;

    public Game(String title, String publisher, int year, String genre) {
        this.title = title;
        this.publisher = publisher;
        this.year = year;
        this.genre = genre;
    }
}
