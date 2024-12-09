package org.example.bookshop.services;

import java.io.IOException;
import java.time.LocalDate;

public interface BookService {
    void seedBooks() throws IOException;
    boolean areBooksImported();
    void printBooksWithReleaseDateAfter(LocalDate date);
    void printAllBooksFromGeorge();
}
