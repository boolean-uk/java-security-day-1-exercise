package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIncludeProperties(value = {"name"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIncludeProperties(value = {"title"})
    private Game game;

    @Column
    private LocalDateTime borrowedAt;

    @Column
    private LocalDateTime returnedAt;


    public Loan(User user, Game game) {
        this.user = user;
        this.game = game;
    }

    public Loan(int id){ this.id = id;}

}
