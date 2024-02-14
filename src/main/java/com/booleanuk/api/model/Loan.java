//package com.booleanuk.api.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonIncludeProperties;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "loans")
//public class Loan {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @OneToMany(mappedBy = "user")
//
//    private List<User> currentlyBorrowedGames;
//
//    public Loan(int id){ this.id = id;}
//
//}
