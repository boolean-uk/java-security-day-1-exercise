package com.booleanuk.api.Models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "video_game_id", nullable = false)
    @JsonIncludeProperties(value = "title")
    private VideoGame videoGame;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIncludeProperties(value = "name")
    private User user;

    @Column
    private String loanedFrom;

    @Column
    private String loanedTo;

    public Loan(String loanedFrom, String loanedTo){
        this.loanedFrom = loanedFrom;
        this.loanedTo = loanedTo;
    }
}
