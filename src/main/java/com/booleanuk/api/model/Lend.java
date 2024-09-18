package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lends")
public class Lend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("lends")
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIgnoreProperties("lends")
    private Game game;

    @Column
    private boolean active;

    public Lend(User user, Game game, boolean active) {
        this.user = user;
        this.game = game;
        this.active = active;
    }

    public Lend(int id) {
        this.id = id;
    }
}
