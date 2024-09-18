package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @Column
    private String title;

    @Column
    private String genre;

    @Column
    private String gameStudio;

    @Column
    private int ageRating;

    @Column
    private int numberOfPlayers;

    @Column
    private boolean occupied;

    @OneToMany(mappedBy = "game")
    @JsonIgnoreProperties("game")
    private List<Lend> lends;

    public Game(int id) {
        this.id = id;
    }

    public Game(String title, String genre, String gameStudio, int ageRating, int numberOfPlayers) {
        this.title = title;
        this.genre = genre;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numberOfPlayers = numberOfPlayers;
    }

    public void addLend(Lend l) {
        this.lends.add(l);
    }

}
