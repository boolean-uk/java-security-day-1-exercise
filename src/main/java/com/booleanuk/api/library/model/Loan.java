package com.booleanuk.api.library.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnoreProperties (value = {"loans"})
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties (value = {"loans"})
    private User user;

    public Loan(Game game, User user) {
        this.game = game;
        this.user = user;
    }

    public Loan(int id) {
        this.id = id;
    }
}
