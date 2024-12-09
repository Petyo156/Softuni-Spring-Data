package org.example.gamestore.service.impls;

import jakarta.validation.ConstraintViolation;
import org.example.gamestore.data.entities.User;
import org.example.gamestore.data.repositories.UserRepository;
import org.example.gamestore.service.dtos.CreateUserDTO;
import org.example.gamestore.service.UserService;
import org.example.gamestore.service.dtos.LoginUserDTO;
import org.example.gamestore.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    private User user;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String createUser(CreateUserDTO createUserDTO) {
        if (!createUserDTO.getPassword().equals(createUserDTO.getConfirmPassword())) {
            return "Passwords do not match";
        }

        if (!validatorUtil.isValid(createUserDTO)) {
            return validatorUtil.validate(createUserDTO).stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));
        }

        if(userRepository.getByEmail(createUserDTO.getEmail()).isPresent()){
            return "Email address is already in use";
        }

        User user = this.modelMapper.map(createUserDTO, User.class);
        if (this.userRepository.count() == 0) {
            user.setAdmin(true);
        }

        userRepository.saveAndFlush(user);
        return String.format("%s was registered successfully!", user.getFullName());
    }

    @Override
    public String loginUser(LoginUserDTO loginUserDTO) {
        Optional<User> byEmailAndPassword = userRepository.findByEmailAndPassword(loginUserDTO.getEmail(), loginUserDTO.getPassword());

        if(byEmailAndPassword.isEmpty()){
            return "Invalid email or password";
        }

        this.user = byEmailAndPassword.get();
        return String.format("%s was logged in successfully!", this.user.getFullName());
    }

    @Override
    public User getLoggedInUser() {
        return this.user;
    }

    @Override
    public boolean isAdmin() {
        return isLoggedIn() && this.user.isAdmin();
    }

    @Override
    public String logout() {
        if(!isLoggedIn()){
            return "No logged in user";
        }
        String name = user.getFullName();
        this.user = null;
        return String.format("%s was logged out.", name);
    }

    @Override
    public boolean isLoggedIn() {
        return this.user != null;
    }
}
