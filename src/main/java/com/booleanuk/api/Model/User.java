package com.booleanuk.api.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String phone;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value ={"users", "games"})
    private List<Game> games;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value ={"users", "games"})
    private Set<Game> prevGames;

    public void borrowGame(Game game){
        if (!games.contains(game)){
            games.add(game);
        }
        prevGames.add(game);
    }

    public void returnGame(Game game){
        if (!games.contains(game)){
            games.remove(game);
        }
    }

}
