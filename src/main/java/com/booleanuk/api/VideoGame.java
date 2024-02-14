package com.booleanuk.api;

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
    private String GameStudio;

    @Column
    private String ageRating;

    @Column
    private int numPlayers;

    @Column
    private String genre;

    @Column
    private boolean available;
}
