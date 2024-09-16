package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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

    private boolean returned;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIncludeProperties("name")
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIncludeProperties("title")
    private Game game;

    public Loan(User user, Game game) {
        this.user = user;
        this.game = game;
        this.returned = false;
    }

}
