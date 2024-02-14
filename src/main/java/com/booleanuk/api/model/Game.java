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

    @Column( name = "title")
    private String title;

    @Column( name = "gameStudio")
    private String gameStudio;

    @Column( name = "ageRating")
    private int ageRating;

    @Column( name = "numberOfPlayers")
    private int numberOfPlayers;

    @Column( name = "genre")
    private String genre;

    public Game(String title, String gameStudio, int ageRating, int numberOfPlayers, String genre){
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numberOfPlayers = numberOfPlayers;
        this.genre = genre;
    }
}
