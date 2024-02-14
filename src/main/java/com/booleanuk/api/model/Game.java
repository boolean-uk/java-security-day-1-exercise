package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @Column(name = "genre")
    private String genre;

    @Column(name = "number_of_players")
    private int numberOfPlayers;

    @Column(name = "isBorrowed")
    private boolean isBorrowed;

    @ManyToOne
    @JoinColumn(name ="borrowed_by")
    @JsonIncludeProperties(value = {"name"})
    @JsonIgnoreProperties("borrowedBy")
    private User borrowedBy;

    public Game(String title, String gameStudio, String genre, int numberOfPlayers) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.genre = genre;
        this.numberOfPlayers = numberOfPlayers;
        this.isBorrowed = false;
    }

    public Game(int id){ this.id = id;}
}
