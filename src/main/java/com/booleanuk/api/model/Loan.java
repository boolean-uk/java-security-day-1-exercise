package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "video_game_id")
	private VideoGame video_game;
	@Column
	private String date;
	@Column
	private boolean returned;

	public Loan(int id) {
		this.id = id;
	}

	public Loan(User user, VideoGame video_game, String date, boolean returned) {
		this.user = user;
		this.video_game = video_game;
		this.date = date;
		this.returned = returned;
	}
}
