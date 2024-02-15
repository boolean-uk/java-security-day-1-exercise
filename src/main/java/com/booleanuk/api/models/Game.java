package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "game")
public class Game extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;
    @Column(name = "game_studio")
    private String gameStudio;
    @Column(name = "age_rating")
    private String ageRating;
    @Column
    private String genre;
    @Column(name = "number_of_players")
    private Integer numOfPlayers;

    @OneToMany(mappedBy = "game")
    @JsonIgnoreProperties("game")
    private List<Lend> lends;
    
    @Override
    public boolean haveNullFields() {
        return title == null || gameStudio == null || ageRating == null || genre == null || numOfPlayers == null;
    }

    @Override
    public void copyOverData(Model model) {
        try {
            Game _game = (Game) model;

            title = _game.title;
            gameStudio = _game.gameStudio;
            ageRating = _game.ageRating;
            genre = _game.genre;
            numOfPlayers = _game.numOfPlayers;

            if (!_game.lends.isEmpty())
                lends = _game.lends;
        }
        catch (Exception e) {}
    }
}
