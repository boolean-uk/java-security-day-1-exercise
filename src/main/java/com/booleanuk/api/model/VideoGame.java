package com.booleanuk.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "video_games")
public class VideoGame {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "game_studio")
	private String gameStudio;

	@Column(name = "age_rating")
	private String ageRating;

	@Column(name = "number_of_players")
	private int numberOfPlayers;

	@Column(name = "genre")
	private String genre;
	@JsonIgnore
	@OneToMany(mappedBy = "video_game")
	private List<Loan> loans;

	public VideoGame(int id) {
		this.id = id;
	}

	public VideoGame(String title, String gameStudio, String ageRating, int numberOfPlayers, String genre) {
		this.title = title;
		this.gameStudio = gameStudio;
		this.ageRating = ageRating;
		this.numberOfPlayers = numberOfPlayers;
		this.genre = genre;
	}
}
