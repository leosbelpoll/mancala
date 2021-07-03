package com.leosbelpoll.mancala.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    private final String email1 = "user1@gmail.com";
    private final String email2 = "user2@gmail.com";

    private Game game;

    @BeforeEach
    void init() {
        game = new Game(User.builder().id(1L).email(email1).build(), User.builder().id(2L).email(email2).build());
        game.setId(1L);
        game.getPlayer1().setId(1L);
        game.getPlayer2().setId(2L);
    }

    @Test
    void whenCreateGameWithConfiguration_whenOK() {
        game = new Game(7,5,User.builder().id(1L).email(email1).build(), User.builder().id(2L).email(email2).build());
        assertEquals(7, game.getPitsNumber());
        assertEquals(5, game.getBallsNumber());
    }
}