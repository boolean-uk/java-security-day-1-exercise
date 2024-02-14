package com.booleanuk.api.model;

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

    @Column(name = "genre")
    private String genre;

    @Column(name = "number_of_players")
    private int numberOfPlayers;

    public Game(String title, String gameStudio, String genre, int numberOfPlayers) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.genre = genre;
        this.numberOfPlayers = numberOfPlayers;
    }

    public Game(int id){ this.id = id;}
}
