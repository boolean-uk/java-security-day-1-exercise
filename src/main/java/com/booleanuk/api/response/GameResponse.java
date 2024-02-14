package com.booleanuk.api.response;

import com.booleanuk.api.videogame.Game;


public class GameResponse extends Response<Game>{

    public GameResponse(Game game) {
        super("success", game);
    }
}
