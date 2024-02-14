package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "borrow_games")
public class BorrowGames {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String borrow_date;

    @Column
    private String return_date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private VideoGame videoGame;

    public BorrowGames(String borrow_date, String return_date) {
        this.borrow_date = borrow_date;
        this.return_date = return_date;
    }

}
