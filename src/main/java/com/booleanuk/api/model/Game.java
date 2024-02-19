package com.booleanuk.api.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "game")
    @JsonIgnoreProperties(value = "game", allowSetters = true)
    private List<Loan> loansHistory;

    @OneToMany(mappedBy = "game")
    @JsonIgnoreProperties(value = "game", allowSetters = true)
    private List<Loan> loans;


    public Game(String title, String gameStudio, int ageRating, String genre, Library library) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.genre = genre;
        this.library = library;
    }


    public Game(int id) {
        this.id = id;
    }



}
