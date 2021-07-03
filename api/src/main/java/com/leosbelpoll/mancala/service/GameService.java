package com.leosbelpoll.mancala.service;

import com.leosbelpoll.mancala.exception.GameNotFoundException;
import com.leosbelpoll.mancala.exception.TurnException;
import com.leosbelpoll.mancala.model.Game;
import com.leosbelpoll.mancala.model.User;
import com.leosbelpoll.mancala.repository.GameRepository;
import com.leosbelpoll.mancala.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

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

    public Game play(Long gameId, Long userId, Integer position) {
        Game game = get(gameId);
        if(!game.getTurn().getId().equals(userId))
            throw new TurnException(String.format("The user whit id %d does not have to play", userId));
        game.play(position);
        playerRepository.save(game.getPlayer1());
        playerRepository.save(game.getPlayer2());

        return gameRepository.save(game);
    }

    public Game get(Long id) {
        Optional<Game> optional = gameRepository.findById(id);
        if (optional.isPresent())
            return optional.get();
        throw new GameNotFoundException("Game not found");
    }
}
