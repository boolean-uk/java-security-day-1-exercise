package com.booleanuk.api.borrowings;


import com.booleanuk.api.users.User;
import com.booleanuk.api.videoGames.VideoGame;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "borrowings")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "video_game_id", nullable = false)
    VideoGame videoGame;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    OffsetDateTime borrowedAt;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    OffsetDateTime returnedAt;


    public Borrowing(User user, VideoGame videoGame) {
        this.user = user;
        this.videoGame = videoGame;
    }

    public Borrowing(int id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        borrowedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        returnedAt = OffsetDateTime.now();
    }
}
