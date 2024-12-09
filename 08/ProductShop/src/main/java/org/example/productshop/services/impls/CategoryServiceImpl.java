package org.example.productshop.services.impls;

import com.google.gson.Gson;
import jakarta.validation.ConstraintViolation;
import org.example.productshop.persistence.entities.Category;
import org.example.productshop.persistence.repositories.CategoryRepository;
import org.example.productshop.services.CategoryService;
import org.example.productshop.services.dtos.CategorySeedDTO;
import org.example.productshop.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    public static final String JSONS_CATEGORIES_PATH = "src/main/resources/jsons/categories.json";

    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    public CategoryServiceImpl(CategoryRepository categoryRepository, Gson gson, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    public String addCategoriesFromJson() throws IOException {
        if (categoryRepository.count() > 0) {
            return "Categories already exist";
        }

        String jsonString = String.join("", Files.readAllLines(Path.of(JSONS_CATEGORIES_PATH)));
        CategorySeedDTO[] categories = gson.fromJson(jsonString, CategorySeedDTO[].class);
        Arrays.stream(categories).forEach(c -> {
            if (!this.validatorUtil.isValid(c)) {
                String collect = this.validatorUtil.validate(c).stream()
                        .map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
                System.out.println(collect);
            } else {
                Category mapped = modelMapper.map(c, Category.class);
                categoryRepository.saveAndFlush(mapped);
            }
        });

        return "Successfully added categories!";
    }


}
