package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String phone;
    @OneToMany
    @JsonIgnore
    @ToString.Exclude
    private List<BorrowGame> borrowGames;

    public boolean haveNullFields() {
        return name == null || email == null;
    }
}
