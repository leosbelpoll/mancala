package com.leosbelpoll.mancala.repository;

import com.leosbelpoll.mancala.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
