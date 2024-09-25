package com.booleanuk.api.Model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String gameStudio;
    private String ageRating;
    private int numberOfPlayers;
    private String genre;
}
