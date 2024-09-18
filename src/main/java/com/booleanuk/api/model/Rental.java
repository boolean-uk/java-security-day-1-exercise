package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rentals")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //private LocalDateTime rent_start;
    private int length_days;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnoreProperties("rentals")
    private Videogame game;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("rentals")
    private User user;

    public Rental(int length_days, Boolean isActive) {
        this.length_days = length_days;
        this.isActive = isActive;
        //this.rent_start = LocalDateTime.now();
    }


    public Rental(int id){
        this.id = id;
    }

}
