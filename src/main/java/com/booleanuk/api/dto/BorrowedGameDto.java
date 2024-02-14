package com.booleanuk.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BorrowedGameDto {
    private int id;
    private String game;
    private String user;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate borrowDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    public BorrowedGameDto(int id, String game, String user, LocalDate borrowDate, LocalDate returnDate) {
        this.id = id;
        this.game = game;
        this.user = user;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }
}
