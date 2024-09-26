package com.booleanuk.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String developer;
    private String publisher;
    private Integer rating;
    private String genre;

    public boolean haveNullFields() {
        return title == null || developer == null || publisher == null || rating == null || genre == null;
    }
}
