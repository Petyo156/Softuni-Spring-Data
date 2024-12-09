package org.example.productshop.services.impls;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import org.example.productshop.persistence.entities.Product;
import org.example.productshop.persistence.entities.User;
import org.example.productshop.persistence.repositories.CategoryRepository;
import org.example.productshop.persistence.repositories.ProductRepository;
import org.example.productshop.persistence.repositories.UserRepository;
import org.example.productshop.services.UserService;
import org.example.productshop.services.dtos.*;
import org.example.productshop.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final String JSONS_USERS_PATH = "src/main/resources/jsons/users.json";

    private final UserRepository userRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, Gson gson, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String addUsersFromJson() throws IOException {
        if (userRepository.count() > 0) {
            return "Users already exist";
        }

        String jsonString = String.join("", Files.readAllLines(Path.of(JSONS_USERS_PATH)));
        UserSeedDTO[] users = gson.fromJson(jsonString, UserSeedDTO[].class);
        Arrays.stream(users).forEach(u -> {
            if (!this.validatorUtil.isValid(u)) {
                String collect = this.validatorUtil.validate(u).stream()
                        .map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
                System.out.println(collect);
            } else {
                User mapped = modelMapper.map(u, User.class);
                userRepository.saveAndFlush(mapped);
            }
        });

        return "Successfully added users!";
    }

    @Transactional
    @Override
    public String successfullySoldProductsJson() throws IOException {
        Set<User> users = userRepository.findAllBySoldIsNotNull();
        List<CertainUsersDTO> userSold = users.stream()
                .map(u -> {
                    CertainUsersDTO certainUsersDTO = this.modelMapper.map(u, CertainUsersDTO.class);
                    certainUsersDTO.setSoldProducts(
                            u.getSoldProducts()
                                    .stream()
                                    .map(p -> this.modelMapper.map(p, ProductsSoldDTO.class))
                                    .collect(Collectors.toSet())
                    );
                    return certainUsersDTO;
                })
                .collect(Collectors.toList());

        try (FileWriter writer = new FileWriter("src/main/resources/jsons/created/zad2.json")) {
            gson.toJson(userSold, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Exported successfully!";
    }
}
