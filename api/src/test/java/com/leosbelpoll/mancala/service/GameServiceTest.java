package com.leosbelpoll.mancala.service;

import com.leosbelpoll.mancala.model.Game;
import com.leosbelpoll.mancala.model.Player;
import com.leosbelpoll.mancala.model.User;
import com.leosbelpoll.mancala.repository.GameRepository;
import com.leosbelpoll.mancala.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GameServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    private GameService gameService;

    private final String email1 = "user1@gmail.com";
    private final String email2 = "user2@gmail.com";

    @BeforeEach
    void setup() {
        gameService = new GameService(userService, gameRepository, playerRepository);
        Mockito.reset();
    }

    @Test
    void whenCreateGame_thenSuccess() {
        Mockito.when(userService.getOrCreate(email1)).thenReturn(User.builder().id(1L).email(email1).build());
        Mockito.when(userService.getOrCreate(email2)).thenReturn(User.builder().id(2L).email(email2).build());

        gameService.create(email1, email2, 6, 4);

        Mockito.verify(userService, Mockito.times(2)).getOrCreate(Mockito.anyString());
        Mockito.verify(playerRepository, Mockito.times(2)).save(Mockito.any(Player.class));
        Mockito.verify(gameRepository, Mockito.times(1)).save(Mockito.any(Game.class));
    }
}