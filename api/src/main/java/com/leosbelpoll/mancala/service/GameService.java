package com.leosbelpoll.mancala.service;

import com.leosbelpoll.mancala.model.Game;
import com.leosbelpoll.mancala.model.User;
import com.leosbelpoll.mancala.repository.GameRepository;
import com.leosbelpoll.mancala.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final UserService userService;

    private final GameRepository gameRepository;

    private final PlayerRepository playerRepository;

    public Game create(String email1, String email2, Integer pits, Integer balls) {
        User user1 = userService.getOrCreate(email1);
        User user2 = userService.getOrCreate(email2);
        Game game = new Game(pits, balls, user1, user2);

        playerRepository.save(game.getPlayer1());
        playerRepository.save(game.getPlayer2());

        return gameRepository.save(game);
    }
}
