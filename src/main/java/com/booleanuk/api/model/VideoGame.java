package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "videoGames")

public class VideoGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "gameStudio")
    private String gameStudio ;

    @Column(name = "ageRating")
    private int ageRating;

    @Column(name = "numPlayers")
    private int numPlayers;

    @Column(name = "genre")
    private String genre;


    public VideoGame(String title, String gameStudio, int ageRating, int numPlayers, String genre) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numPlayers = numPlayers;
        this.genre = genre;
    }


}
