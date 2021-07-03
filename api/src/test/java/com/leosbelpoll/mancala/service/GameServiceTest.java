package com.leosbelpoll.mancala.service;

import com.leosbelpoll.mancala.exception.GameNotFoundException;
import com.leosbelpoll.mancala.exception.TurnException;
import com.leosbelpoll.mancala.model.Game;
import com.leosbelpoll.mancala.model.Player;
import com.leosbelpoll.mancala.model.User;
import com.leosbelpoll.mancala.repository.GameRepository;
import com.leosbelpoll.mancala.repository.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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

    @Test
    void whenGetGame_thenThrowException() {
        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(GameNotFoundException.class, () -> gameService.get(1L));
    }

    @Test
    void whenGetGame_thenSuccess() {
        Game gameMock = new Game(User.builder().id(1L).email(email1).build(), User.builder().id(2L).email(email2).build());
        gameMock.setId(1L);
        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(gameMock));
        Assertions.assertEquals(1L, gameService.get(1L).getId());
    }


    @Test
    void whenPlayGame_thenTurnException() {
        Game gameMock = new Game(User.builder().id(1L).email(email1).build(), User.builder().id(2L).email(email2).build());
        gameMock.setId(1L);
        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(gameMock));

        Assertions.assertThrows(TurnException.class,
                () -> gameService.play(1L, gameMock.getTurn().getId() == 1L ? 2L: 1L, 0));
    }

    @Test
    void whenPlayGame_thenOk() {
        Game gameMock = new Game(User.builder().id(1L).email(email1).build(), User.builder().id(2L).email(email2).build());
        gameMock.setId(1L);
        gameMock.getPlayer1().setId(1L);
        gameMock.getPlayer2().setId(2L);
        Mockito.when(gameRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(gameMock));

        gameService.play(1L, gameMock.getTurn().getId(), 0);

        Mockito.verify(playerRepository, Mockito.times(2)).save(Mockito.any(Player.class));
        Mockito.verify(gameRepository, Mockito.times(1)).save(Mockito.any(Game.class));
    }
}