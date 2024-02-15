package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "lend")
public class Lend extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "return_date")
    private String returnDate;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "username", "email" })
    private User user;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "title", "game_studio", "age_rating", "number_of_players" })
    private Game game;

    @Override
    public boolean haveNullFields() {
        return returnDate == null;
    }

    @Override
    public void copyOverData(Model model) {
        try {
            Lend _lend = (Lend) model;

            returnDate = _lend.returnDate;

            user = _lend.user;
            game = _lend.game;
        }
        catch (Exception e) {}
    }
}
