package com.booleanuk.api.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
    private Integer ageRating;

    @Column(name = "number_of_player")
    private Integer numberOfPlayer;

    @Column(name = "genre")
    private String genre;

    @OneToMany(mappedBy = "game")
    @JsonIgnoreProperties (value = {"name"})
    private List<Loan> loans;



    public Game(String title, String gameStudio, int ageRating, int numberOfPlayer, String genre) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numberOfPlayer = numberOfPlayer;
        this.genre = genre;
    }

    public Game(int id) {
        this.id = id;
    }
}
