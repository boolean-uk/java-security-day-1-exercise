package com.booleanuk.api.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "video_games")
@Getter
@Setter
@NoArgsConstructor
public class VideoGame {
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
    private int numPlayers;

    @Column
    private String genre;

    @Column
    private boolean available;

    public VideoGame(String title, String gameStudio, String ageRating, int numPlayers, String genre) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numPlayers = numPlayers;
        this.genre = genre;
        this.available = true;
    }

    public VideoGame(int id){
        this.id = id;
    }
}
