package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String phone;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Borrow> borrow;
}
