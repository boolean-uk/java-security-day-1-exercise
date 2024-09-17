package com.booleanuk.api.games;

import com.booleanuk.api.developers.Developer;
import com.booleanuk.api.users.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "games")
public class Game {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String genre;

  @ManyToOne(cascade = CascadeType.ALL)
  @JsonIgnoreProperties(value = "games")
  @JoinColumn(name = "developer_id")
  private Developer developer;

  @ManyToOne(cascade = CascadeType.ALL)
  @JsonIgnoreProperties(value = "borrowedGames")
  @JoinColumn(name = "borrowing_user_id")
  private User borrowedTo;
}