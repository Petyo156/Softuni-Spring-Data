package org.example.productshop.controllers;

import org.example.productshop.persistence.repositories.CategoryRepository;
import org.example.productshop.persistence.repositories.ProductRepository;
import org.example.productshop.persistence.repositories.UserRepository;
import org.example.productshop.services.CategoryService;
import org.example.productshop.services.ProductService;
import org.example.productshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Main implements CommandLineRunner {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public Main(CategoryService categoryService, ProductService productService, UserService userService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        String users = userService.addUsersFromJson();
        String categories = categoryService.addCategoriesFromJson();
        String products = productService.addProductsFromJson();

        System.out.println(String.join("\n", users, categories, products));
        System.out.println(productService.getCertainProducts());
        System.out.println(userService.successfullySoldProductsJson());
    }


}
