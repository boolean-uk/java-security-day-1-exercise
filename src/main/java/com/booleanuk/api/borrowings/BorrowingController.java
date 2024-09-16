package com.booleanuk.api.borrowings;


import com.booleanuk.api.Exceptions.BadRequestException;
import com.booleanuk.api.Exceptions.NotFoundException;
import com.booleanuk.api.users.User;
import com.booleanuk.api.users.UserRepository;
import com.booleanuk.api.videoGames.VideoGame;
import com.booleanuk.api.videoGames.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/{userid}/borrowings")
public class BorrowingController {
    @Autowired
    BorrowingRepository borrowingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VideoGameRepository videoGameRepository;


    @GetMapping()
    public ResponseEntity<?> getAll(@PathVariable int userid) {
      User user =   this.userRepository.findById(userid).orElseThrow(
                () -> new NotFoundException("No movie with that id: " + userid + " found")
        );
        List<Borrowing> borrowings = borrowingRepository.findByUser(user);

        return ResponseEntity.ok(borrowings);
    }

    @PostMapping("videoGames/{videoGameId}")
    public ResponseEntity<?> create(@RequestBody Borrowing borrowing, @PathVariable int userid, @PathVariable int videoGameId ) {
        User user = this.userRepository.findById(userid).orElseThrow(
                () -> new NotFoundException("No user with that id: " + userid + " found")
        );
        VideoGame videoGame = this.videoGameRepository.findById(videoGameId).orElseThrow(
                () -> new NotFoundException("No video game with that id: " + videoGameId + " found")
        );
        try {
            borrowing.setUser(user);
            borrowing.setVideoGame(videoGame);
            borrowingRepository.save(borrowing);
            return new ResponseEntity<>(borrowing, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }


}
