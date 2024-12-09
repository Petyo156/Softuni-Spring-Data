package org.example.productshop.services;

import java.io.IOException;

public interface ProductService {
    String addProductsFromJson() throws IOException;
    String getCertainProducts() throws IOException;
}
