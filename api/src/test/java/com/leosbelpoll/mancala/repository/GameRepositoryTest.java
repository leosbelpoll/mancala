package com.leosbelpoll.mancala.repository;

import com.leosbelpoll.mancala.model.Game;
import com.leosbelpoll.mancala.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    void whenCreateGame_thenOk(){
        String email1 = "user1@gmail.com";
        String email2 = "user2@gmail.com";

        User user1 = User.builder().email(email1).id(1L).build();
        User user2 = User.builder().email(email2).id(2L).build();
        int pitsNumber = 6;
        int ballsNumber = 4;

        Game game = new Game(pitsNumber,ballsNumber, user1, user2);
        assertDoesNotThrow(() -> gameRepository.save(game));
        assertTrue(game.getId() > 0);
    }
}