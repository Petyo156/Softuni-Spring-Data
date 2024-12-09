package org.example.gamestore.controller;

import org.example.gamestore.service.GameService;
import org.example.gamestore.service.dtos.AddGameDTO;
import org.example.gamestore.service.dtos.CreateUserDTO;
import org.example.gamestore.service.UserService;
import org.example.gamestore.service.dtos.EditGameDTO;
import org.example.gamestore.service.dtos.LoginUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;
    private final Scanner scanner;

    @Autowired
    public Main(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        String line;
        while (!(line = scanner.nextLine()).equals("stop")) {
            String[] tokens = line.split("\\|");
            String output = "";
            switch (tokens[0]) {
                case "RegisterUser":
                    CreateUserDTO createUserDTO = new CreateUserDTO(
                            tokens[1], tokens[2], tokens[3], tokens[4]
                    );
                    output = userService.createUser(createUserDTO);
                    break;
                case "LoginUser":
                    LoginUserDTO loginUserDTO = new LoginUserDTO(
                            tokens[1], tokens[2]
                    );
                    output = userService.loginUser(loginUserDTO);
                    break;
                case "Logout":
                    output = userService.logout();
                    break;
                case "AddGame":
                    AddGameDTO addGameDTO = new AddGameDTO(
                            tokens[1], new BigDecimal(tokens[2]), Double.parseDouble(tokens[3]), tokens[4],
                            tokens[5], tokens[6], LocalDate.parse(tokens[7], DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    );
                    output = gameService.addGame(addGameDTO);
                    break;
                case "EditGame":
                    EditGameDTO editGameDTO = new EditGameDTO();
                    editGameDTO.setId(Long.parseLong(tokens[1]));
                    Arrays.stream(tokens).skip(2).forEach(v -> {
                                String[] split = v.split("=");
                                String field = split[0];
                                if("price".equals(field)) {
                                    editGameDTO.setPrice(new BigDecimal(split[1]));
                                } else if("size".equals(field)) {
                                    editGameDTO.setSize(Double.parseDouble(split[1]));
                                } else if("trailer".equals(field)) {
                                    editGameDTO.setTrailer(split[1]);
                                } else if("title".equals(field)) {
                                    editGameDTO.setTitle(split[1]);
                                } else if("description".equals(field)) {
                                    editGameDTO.setDescription(split[1]);
                                } else if("thumbnail".equals(field)) {
                                    editGameDTO.setImageThumbnail(split[1]);
                                } else if("date".equals(field)) {
                                    editGameDTO.setReleaseDate(LocalDate.parse(split[1]));
                                }
                            }
                        );
                    output = gameService.editGame(editGameDTO);
                    break;
                case "DeleteGame":
                    output = gameService.deleteGame(Long.parseLong(tokens[1]));
                    break;
                case "AllGames":
                    output = gameService.allGames();
                    break;
                case "DetailGame":
                    output = gameService.detailGame(tokens[1]);
                    break;
            }
            if (!output.isEmpty()) {
                System.out.println(output);
            }
        }
    }
}
