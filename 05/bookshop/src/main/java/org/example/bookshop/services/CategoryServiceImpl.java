package org.example.bookshop.services;

import org.example.bookshop.persistence.entities.Category;
import org.example.bookshop.persistence.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private static final String CATEGORIES_PATH = "src/main/resources/files/categories.txt";

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories() throws IOException {
        if (!hasCategories()) {
            Set<Category> categories = new HashSet<>();
            Files.readAllLines(Path.of(CATEGORIES_PATH)).forEach(line -> {
                Category category = new Category(line);
                categories.add(category);
            });
            categoryRepository.saveAll(categories);
        }
        System.out.println("Count categories: " + this.categoryRepository.count());
    }

    @Override
    public boolean hasCategories() {
        return categoryRepository.count() > 0;
    }
}
