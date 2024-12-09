package org.example.gamestore.service;

import org.example.gamestore.data.entities.User;
import org.example.gamestore.service.dtos.CreateUserDTO;
import org.example.gamestore.service.dtos.LoginUserDTO;

public interface UserService {
    String createUser(CreateUserDTO createUserDTO);

    String loginUser(LoginUserDTO loginUserDTO);

    User getLoggedInUser();

    boolean isAdmin();

    String logout();

    boolean isLoggedIn();
}
