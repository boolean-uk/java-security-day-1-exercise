package com.booleanuk.api.model;


import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String gameStudio;

    @Column
    private int ageRating;

    @Column
    private String genre;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    @JsonIncludeProperties({"id", "name", "createdAt", "updatedAt"})
    private Library library;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIncludeProperties({"id", "name", "createdAt", "updatedAt"})
    private User user;

    public Game(String title, String gameStudio, int ageRating, String genre, Library library) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.genre = genre;
        this.library = library;
    }
}
