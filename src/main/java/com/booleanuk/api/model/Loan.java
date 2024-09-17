package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String dayBorrowed;

    @Column
    private String dayReturned;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIncludeProperties(value = {"title", "studio", "rating", "playerCount", "genre"})
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIncludeProperties(value = {"name", "email"})
    private User user;

    public Loan(int id){
        this.id = id;
    }
}
