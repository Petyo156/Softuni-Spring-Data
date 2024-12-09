package org.example.productshop.services.impls;

import com.google.gson.Gson;
import jakarta.validation.ConstraintViolation;
import org.example.productshop.persistence.entities.Category;
import org.example.productshop.persistence.entities.Product;
import org.example.productshop.persistence.repositories.CategoryRepository;
import org.example.productshop.persistence.repositories.ProductRepository;
import org.example.productshop.persistence.repositories.UserRepository;
import org.example.productshop.services.ProductService;
import org.example.productshop.services.dtos.CategorySeedDTO;
import org.example.productshop.services.dtos.CertainProductsDTO;
import org.example.productshop.services.dtos.ProductSeedDTO;
import org.example.productshop.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String JSONS_PRODUCTS_PATH = "src/main/resources/jsons/products.json";
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, Gson gson, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String addProductsFromJson() throws IOException {
        if (productRepository.count() > 0) {
            return "Products already exist";
        }

        String jsonString = String.join("", Files.readAllLines(Path.of(JSONS_PRODUCTS_PATH)));
        ProductSeedDTO[] products = gson.fromJson(jsonString, ProductSeedDTO[].class);
        Arrays.stream(products).forEach(p -> {
            if (!this.validatorUtil.isValid(p)) {
                String collect = this.validatorUtil.validate(p).stream()
                        .map(ConstraintViolation::getMessage).collect(Collectors.joining("\n"));
                System.out.println(collect);
            } else {
                Product mapped = modelMapper.map(p, Product.class);

                Random random = new Random();
                setSellerAndBuyer(random, mapped);
                setRandomCategories(random, mapped);

                productRepository.saveAndFlush(mapped);
            }
        });

        return "Successfully added products!";
    }

    @Override
    public String getCertainProducts() throws IOException {
        Set<Product> productsByPrice = productRepository.getProductsByPriceBetweenAndBuyerIsNull(
                BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

        if (productsByPrice.isEmpty()) {
            return "No products found";
        }

        List<CertainProductsDTO> mappedProducts = new ArrayList<>();
        productsByPrice.forEach(product -> {
            CertainProductsDTO mapped = modelMapper.map(product, CertainProductsDTO.class);
            mapped.setSeller(product.getSeller().getFirstName() + " " + product.getSeller().getLastName());
            mappedProducts.add(mapped);
        });
        try (FileWriter writer = new FileWriter("src/main/resources/jsons/created/zad1.json")) {
            gson.toJson(mappedProducts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Exported successfully!";
    }

    private void setRandomCategories(Random random, Product mapped) {
        long randomCategoryId1;
        long randomCategoryId2;
        do {
            randomCategoryId1 = random.nextLong(1, categoryRepository.count() + 1);
            randomCategoryId2 = random.nextLong(1, categoryRepository.count() + 1);
        } while (randomCategoryId1 == randomCategoryId2);

        mapped.setCategories(Set.of(categoryRepository.findById(randomCategoryId1).orElse(null),
                categoryRepository.findById(randomCategoryId2).orElse(null)));
    }

    private void setSellerAndBuyer(Random random, Product mapped) {
        long randomBuyerId;
        long randomSellerId;
        do {
            randomBuyerId = random.nextLong(1, userRepository.count() + 1);
            randomSellerId = random.nextLong(1, userRepository.count() + 1);
        } while (randomBuyerId == randomSellerId);


        mapped.setBuyer(userRepository.findById(randomBuyerId).get());
        mapped.setSeller(userRepository.findById(randomSellerId).get());
    }
}
