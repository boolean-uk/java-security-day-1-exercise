package com.booleanuk.api.models;

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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String ageRating;

    @Column(nullable = false)
    private int numberOfPlayers;

    public Game(String title, String ageRating, int numberOfPlayers) {
        this.title = title;
        this.ageRating = ageRating;
        this.numberOfPlayers = numberOfPlayers;
    }
}