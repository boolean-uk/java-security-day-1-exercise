package com.booleanuk.api.developers;

import com.booleanuk.api.games.Game;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "developers")
public class Developer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties(value = "developer", allowSetters = true)
  private List<Game> games;
}