package org.example.gamestore.service.impls;

import jakarta.validation.ConstraintViolation;
import org.example.gamestore.data.entities.Game;
import org.example.gamestore.data.repositories.GameRepository;
import org.example.gamestore.data.repositories.UserRepository;
import org.example.gamestore.service.GameService;
import org.example.gamestore.service.UserService;
import org.example.gamestore.service.dtos.AddGameDTO;
import org.example.gamestore.service.dtos.EditGameDTO;
import org.example.gamestore.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper, UserService userService) {
        this.gameRepository = gameRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public String addGame(AddGameDTO addGameDTO) {
        if (!this.validatorUtil.isValid(addGameDTO)) {
            return this.validatorUtil.validate(addGameDTO)
                    .stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        }
        if(userService.isAdmin()){
            Game game = this.modelMapper.map(addGameDTO, Game.class);
            this.gameRepository.saveAndFlush(game);
            return String.format("Added %s", game.getTitle());
        }
        return "Admin privileges necessary to add games.";
    }

    @Override
    public String editGame(EditGameDTO editGameDTO) {
        if (!this.validatorUtil.isValid(editGameDTO)) {
            return this.validatorUtil.validate(editGameDTO)
                    .stream().map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        }

        Optional<Game> gameOptional = gameRepository.findGameById(editGameDTO.getId());

        if(gameOptional.isEmpty()){
            return "Game with given id does not exist.";
        }

        if(!userService.isAdmin()){
            return "Admin privileges necessary to add games.";
        }

        Game game = gameOptional.get();

        if(editGameDTO.getTitle() != null){
            game.setTitle(editGameDTO.getTitle());
        }
        if(editGameDTO.getDescription() != null){
            game.setDescription(editGameDTO.getDescription());
        }
        if(editGameDTO.getPrice() != null){
            game.setPrice(editGameDTO.getPrice());
        }
        if(editGameDTO.getImageThumbnail() != null){
            game.setImageThumbnail(editGameDTO.getImageThumbnail());
        }
        if(editGameDTO.getReleaseDate() != null){
            game.setReleaseDate(editGameDTO.getReleaseDate());
        }
        if(editGameDTO.getTrailer() != null){
            game.setTrailer(editGameDTO.getTrailer());
        }
        if(editGameDTO.getSize() != null){
            game.setSize(editGameDTO.getSize());
        }

        this.gameRepository.saveAndFlush(game);

        return String.format("Edited %s", game.getTitle());
    }

    @Override
    public String deleteGame(long id) {
        Optional<Game> gameById = gameRepository.findGameById(id);

        if(gameById.isEmpty()){
            return "Game with given id does not exist.";
        }

        if(!userService.isAdmin()){
            return "Admin privileges necessary to delete games.";
        }

        gameRepository.delete(gameById.get());
        return "Game deleted successfully.";
    }

    @Override
    public String allGames() {
        return gameRepository.findAll().stream().map(Game::toString).collect(Collectors.joining("\n"));
    }

    @Override
    public String detailGame(String title) {
        Optional<Game> gameByTitle = gameRepository.findGameByTitle(title);

        if(gameByTitle.isEmpty()){
            return "Game with given title does not exist.";
        }

        Game game = gameByTitle.get();
        return String.format(
                "Title: %s |%n Trailer: %s |%n Description: %s |%n Price: %s |%n Size: %.2f GB |%n Image Thumbnail: %s |%n Release Date: %s|",
                game.getTitle(),
                game.getTrailer(),
                game.getDescription(),
                game.getPrice() != null ? game.getPrice().toString() : "N/A",
                game.getSize(),
                game.getImageThumbnail(),
                game.getReleaseDate() != null ? game.getReleaseDate().toString() : "N/A"
        );
    }


}
