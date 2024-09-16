package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

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
    private int age;

    @Column
    private int numOfPlayers;

    @Column
    private String genre;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"title", "game_studio", "genre", "num_of_players", "age"})
    private User user;

    public Game() {
    }

    public Game(int id) {
        this.id = id;
    }

    public Game(String title, String gameStudio, int age, int numOfPlayers, String genre){
        this.title = title;
        this.gameStudio = gameStudio;
        this.age = age;
        this.numOfPlayers = numOfPlayers;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setUser(User user){
        this.user = user;
    }


}
