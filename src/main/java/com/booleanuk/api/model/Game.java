package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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
    private String ageRating;

    @Column(name = "num_players")
    private int numPlayers;

    @Column(name = "genre")
    private String genre;

    @Column(name = "is_borrowed")
    private boolean isBorrowed;

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    private List<Rent> rents;

    public Game(String title, String gameStudio, String ageRating, int numPlayers, String genre, boolean isBorrowed) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numPlayers = numPlayers;
        this.genre = genre;
        this.isBorrowed = isBorrowed;
    }
    public Game(int id){
        this.id = id;
    }
}