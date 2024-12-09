package org.example.gamestore.service;

import org.example.gamestore.data.entities.Game;
import org.example.gamestore.service.dtos.AddGameDTO;
import org.example.gamestore.service.dtos.EditGameDTO;

import java.util.Optional;

public interface GameService {
    String addGame(AddGameDTO addGameDTO);

    String editGame(EditGameDTO editGameDTO);

    String deleteGame(long l);

    String allGames();

    String detailGame(String title);
}
