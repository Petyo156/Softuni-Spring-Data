package org.example.gamestore.data.repositories;

import org.example.gamestore.data.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findGameById(Long id);

    Optional<Game> findGameByTitle(String title);

}
