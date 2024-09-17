package com.booleanuk.api.users;

import com.booleanuk.api.games.Game;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private int age;

  @OneToMany(mappedBy = "borrowedTo", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties(value = "borrowedTo", allowSetters = true)
  private List<Game> borrowedGames;
}
