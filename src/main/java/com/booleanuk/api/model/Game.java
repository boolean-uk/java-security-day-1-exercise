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

    @Column(name = "gamestudio")
    private String gamestudio;

    @Column(name = "agerating")
    private String agerating;

    @Column(name = "numberOfPlayers")
    private Integer numberOfPlayers;

    @Column(name = "genre")
    private String genre;

    public Game(String title, String gamestudio, String agerating, int numberOfPlayers, String genre) {
        this.title = title;
        this.gamestudio = gamestudio;
        this.agerating = agerating;
        this.numberOfPlayers = numberOfPlayers;
        this.genre = genre;
    }

    public Game(int id) {
        this.id = id;
    }
}
