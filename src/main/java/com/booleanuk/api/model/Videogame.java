package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "videogames")
public class Videogame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String genre;
    private String game_studio;
    private Integer age_rating;

    @OneToMany(mappedBy = "game")
    @JsonIgnoreProperties("game")
    private List<Rental> rentals;

    public Videogame(String title, String genre, String game_studio, Integer age_rating) {
        this.title = title;
        this.genre = genre;
        this.game_studio = game_studio;
        this.age_rating = age_rating;
    }

    public Videogame(int id){
        this.id = id;
    }

}
