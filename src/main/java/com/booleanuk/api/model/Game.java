package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "games")
public class Game {
    //Title, GameStudio, Age Rating, Number Of Players, Genre

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private int numPlayers;

    @Column
    private int ageRating;

    @Column
    private String gameStudio;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIncludeProperties(value = {"id"})
    private User user;

    public Game(String title, int numPlayers, int ageRating, String gameStudio) {
        this.title = title;
        this.numPlayers = numPlayers;
        this.ageRating = ageRating;
        this.gameStudio = gameStudio;
    }

    public Game(int id){
        this.id = id;
    }
}