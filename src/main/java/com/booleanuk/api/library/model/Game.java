package com.booleanuk.api.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "games")

public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String gameStudio;

    private String ageRating;

    private int numberOfPlayers;

    private String genre;

    private boolean onLoan;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("games")
    private User user;



    @PrePersist
    public void onCreate(){
        this.onLoan = false;
    }

    public Game(String title, String gameStudio, String ageRating, int numberOfPlayers, String genre, boolean onLoan) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numberOfPlayers = numberOfPlayers;
        this.genre = genre;
        this.onLoan = onLoan;
    }
}
