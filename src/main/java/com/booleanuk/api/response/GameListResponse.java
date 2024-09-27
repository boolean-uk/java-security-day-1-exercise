package com.booleanuk.api.response;

import com.booleanuk.api.videogame.Game;

import java.util.List;

public class GameListResponse extends Response<List<Game>>{

    public GameListResponse(List<Game> data) {
        super("success", data);
    }
}
