package com.booleanuk.api.Model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LibraryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
}
