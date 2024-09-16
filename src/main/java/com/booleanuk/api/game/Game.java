package com.booleanuk.api.game;

import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;
    @Column
    private String gameStudio;
    @Column
    private String ageRating;
    @Column
    private String numberOfPlayers;
    @Column
    private String genre;

    // No-argument constructor
    public Game() {}

    public Game(String title, String gameStudio, String ageRating, String numberOfPlayers, String genre) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numberOfPlayers = numberOfPlayers;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGameStudio() {
        return gameStudio;
    }

    public void setGameStudio(String gameStudio) {
        this.gameStudio = gameStudio;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
