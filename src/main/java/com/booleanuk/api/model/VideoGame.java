package com.booleanuk.api.model;


import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "videogames")
public class VideoGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String genre;

    @Column
    private String developer;

    @Column
    private String publisher;

    @Column
    private int year;

    @Column(name = "age_Rating")
    private int ageRating;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false )
    @JsonIncludeProperties(value = {"id","name", "location"})
    private User user;

    private VideoGame(int id) {
        this.id = id;
    }

    public VideoGame(String title, String genre, String developer, String publisher, int year, int ageRating, User user) {
        this.title = title;
        this.genre = genre;
        this.developer = developer;
        this.publisher = publisher;
        this.year = year;
        this.ageRating = ageRating;
        this.user = user;
    }
}
