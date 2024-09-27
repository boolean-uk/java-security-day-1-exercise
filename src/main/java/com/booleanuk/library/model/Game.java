package com.booleanuk.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    private Integer id;

    @Column
    private String title;

    @Column
    private String gameStudio;

    @Column
    private String ageRating;

    @Column
    private String genre;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<GameLoan> loans;
}
