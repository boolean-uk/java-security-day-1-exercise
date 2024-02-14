package com.booleanuk.api.library.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "game_studio")
    private String gameStudio;

    @Column(name = "age_rating")
    private int ageRating;

    @Column(name = "number_of_player")
    private int numberOfPlayer;

    @Column(name = "genre")
    private String genre;

    public Game(String title, String gameStudio, int ageRating, int numberOfPlayer, String genre) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numberOfPlayer = numberOfPlayer;
        this.genre = genre;
    }
}
