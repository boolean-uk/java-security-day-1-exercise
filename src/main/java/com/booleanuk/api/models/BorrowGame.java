package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BorrowGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIncludeProperties(value = {"id", "title"})
    private Game game;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIncludeProperties(value = {"id", "name"})
    private Customer customer;
    private boolean isActive = false;
    @CreationTimestamp
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
