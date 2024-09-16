package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "games")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String rating;
    private String description;
    private String genre;

    public Game(String title, String rating, String description, String genre) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.genre = genre;
    }

    public Game(int id) {
        this.id = id;
    }
}
