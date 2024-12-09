package org.example.bookshop.services;

import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException;
    boolean hasCategories();
}
