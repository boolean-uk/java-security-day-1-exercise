package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "videogames")
public class Videogame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "studio")
    private String studio;

    @Column(name = "age_rating")
    private String ageRating;

    @Column(name = "genre")
    private String genre;

    public Videogame(String title, String studio, String ageRating, String genre) {
        this.title = title;
        this.studio = studio;
        this.ageRating = ageRating;
        this.genre = genre;
    }

    public Videogame(int id) {
        this.id = id;
    }
}
