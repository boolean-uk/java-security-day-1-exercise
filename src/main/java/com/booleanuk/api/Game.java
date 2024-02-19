package com.booleanuk.api;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "age")
    private int age;

    @Column(name = "genre")
    private String genre;

    public Game(String title, String author, String publisher, int age, String genre) {
        this.title = title;
        this.publisher = publisher;
        this.age = age;
        this.genre = genre;
    }

    private Game(int id) {
        this.id = id;
    }
}