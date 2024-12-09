package org.example.bookshop.services;

import java.io.IOException;
import java.time.LocalDate;

public interface AuthorService {
    void seedAuthors() throws IOException;
    boolean hasAuthors();

    void printAllAuthorsWithOneBookAfter1990();
    void printAllAuthorsOrderedByNumberOfBooks();
}
