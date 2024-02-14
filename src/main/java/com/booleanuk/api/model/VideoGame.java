package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "video_game")
public class VideoGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private String gameStudio;
    @Column
    private String ageRating;
    @Column
    private String genre;

    @OneToMany(mappedBy = "videoGame", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<BorrowGames> borrowGamesList;

    @JoinColumn(name = "borrowed_by")
    @OneToOne
    private User user;


    public VideoGame(String title, String gameStudio, String ageRating, String genre){
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.genre = genre;
    }
}
