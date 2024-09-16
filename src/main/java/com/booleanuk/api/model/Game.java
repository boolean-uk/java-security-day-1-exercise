package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Column(name = "title")
    private String title;
    @Column(name = "game_studio")
    private String gameStudio;
    @Column(name = "age_rating")
    private int ageRating;
    @Column(name = "number_of_players")
    private int numberOfPlayers;
    @Column(name = "genre")
    private String genre;

    public Game(String title, String gameStudio, int ageRating, int numberOfPlayers, String genre) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numberOfPlayers = numberOfPlayers;
        this.genre = genre;
    }

    public Game(int id) {
        this.id = id;
    }
}
