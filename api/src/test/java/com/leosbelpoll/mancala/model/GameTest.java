package com.leosbelpoll.mancala.model;

import com.leosbelpoll.mancala.exception.PlayException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void whenWinPlayer1_whenFinishGame() {
        Arrays.fill(game.getPlayer1().getPits(), 0);
        Arrays.fill(game.getPlayer2().getPits(), 0);
        game.getPlayer1().setHut(20);
        game.getPlayer2().setHut(10);

        assertTrue(game.checkEndGame());
        game.endGame();
        assertEquals(Status.FINISHED, game.getStatus());
        assertEquals(1L, game.getWinner().getId());
    }

    @Test
    void whenWinPlayer2_whenFinishGame() {
        Arrays.fill(game.getPlayer1().getPits(), 0);
        Arrays.fill(game.getPlayer2().getPits(), 0);
        game.getPlayer2().setHut(20);
        game.getPlayer1().setHut(10);

        assertTrue(game.checkEndGame());
        game.endGame();
        assertEquals(Status.FINISHED, game.getStatus());
        assertEquals(2L, game.getWinner().getId());
    }

    @Test
    void whenFinishGameAndAndNotWinner_whenFinishGame() {
        Arrays.fill(game.getPlayer1().getPits(), 0);
        game.getPlayer1().setHut(20);
        Arrays.fill(game.getPlayer2().getPits(), 0);
        game.getPlayer2().setHut(20);

        Assertions.assertTrue(game.checkEndGame());
        game.endGame();
        assertEquals(Status.FINISHED, game.getStatus());
        assertEquals(null, game.getWinner());
    }

    @Test
    void whenValidErrorPosition_whenThrowException() {
        assertThrows(PlayException.class,
                () -> game.validPosition(game.getPlayer1(), -1));
        game.getPlayer1().getPits()[0] = 0;
        assertThrows(PlayException.class,
                () -> game.validPosition(game.getPlayer1(), 0));
    }

    @Test
    void whenPlayPositionWith0Balls_whenGetOpponentBalls() {
        game.setTurn(User.builder().id(1L).email(email1).build());
        game.getPlayer1().getPits()[4] = 0;
        game.getPlayer1().getPits()[3] = 1;

        game.play(3);

        assertEquals(5, game.getPlayer1().getHut());
        assertEquals(0, game.getPlayer2().getHut());
        assertEquals(0, game.getPlayer1().getPits()[4]);
        assertEquals(0, game.getPlayer2().getPits()[1]);
    }

    @Test
    void whenPlayWithManyBalls_whenOk() {
        game.setTurn(User.builder().id(2L).email(email1).build());
        game.getPlayer2().getPits()[5] = 10;

        game.play(5);

        assertEquals(1, game.getPlayer2().getHut());
        assertEquals(5, game.getPlayer2().getPits()[0]);
        assertEquals(5, game.getPlayer2().getPits()[1]);
        assertEquals(5, game.getPlayer2().getPits()[2]);
    }
}