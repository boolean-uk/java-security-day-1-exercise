package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "libraries")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String createdAt;

    @Column
    private String updatedAt;

    @OneToMany(mappedBy = "library")
    @JsonIgnoreProperties(value = "library", allowSetters = true)
    private List<Game> games;



    public Library(int id) {
        this.id = id;
    }

    public Library(String name, String createdAt, String updatedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
