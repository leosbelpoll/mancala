package com.leosbelpoll.mancala.repository;

import com.leosbelpoll.mancala.model.Player;
import com.leosbelpoll.mancala.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PlayerRepositoryTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void whenCreatePlayer_thenOk(){
        String email = "user1@gmail.com";
        User user = User.builder().email(email).id(1L).build();

        int pitsNumber = 6;
        int ballsNumber = 4;

        int [] pits = new int[pitsNumber];
        Arrays.fill(pits, ballsNumber);

        Player player = Player.builder().pits(pits).user(user).build();;

        assertDoesNotThrow(() -> playerRepository.save(player));
        assertTrue(player.getId() > 0);
        assertEquals(email, player.getUser().getEmail());
        assertEquals(pitsNumber, player.getPits().length);
        assertEquals(ballsNumber, player.getPits()[0]);
    }

}