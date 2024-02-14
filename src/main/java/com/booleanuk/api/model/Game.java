package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
    private String ageRating;

    @Column(name = "num_players")
    private int numPlayers;

    @Column(name = "genre")
    private String genre;

    public Game(String title, String gameStudio, String ageRating, int numPlayers, String genre) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numPlayers = numPlayers;
        this.genre = genre;
    }
}