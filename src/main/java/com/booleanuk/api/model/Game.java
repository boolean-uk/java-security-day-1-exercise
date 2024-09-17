package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "studio")
    private String studio;

    @Column(name = "rating")
    private int rating;

    @Column(name = "playerCount")
    private int playerCount;

    @Column(name = "genre")
    private String genre;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("game")
    private List<Loan> loans;

    public Game(String title, String studio, int rating, int playerCount, String genre) {
        this.title = title;
        this.studio = studio;
        this.rating = rating;
        this.playerCount = playerCount;
        this.genre = genre;
    }

    public Game(int id){
        this.id = id;
    }
}
