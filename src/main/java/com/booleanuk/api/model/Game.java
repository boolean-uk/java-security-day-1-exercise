package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private int ageRating;

    @Column
    private String gameStudio;

    @Column
    private String genre;

    @OneToMany
    private List<BorrowedGame> borrowedGames;

    public Game(String title, int ageRating, String gameStudio, String genre) {
        this.title = title;
        this.ageRating = ageRating;
        this.gameStudio = gameStudio;
        this.genre = genre;
    }
}
