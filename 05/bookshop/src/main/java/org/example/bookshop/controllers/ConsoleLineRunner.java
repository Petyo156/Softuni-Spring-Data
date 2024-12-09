package org.example.bookshop.controllers;

import org.example.bookshop.persistence.repositories.AuthorRepository;
import org.example.bookshop.persistence.repositories.BookRepository;
import org.example.bookshop.persistence.repositories.CategoryRepository;
import org.example.bookshop.services.AuthorService;
import org.example.bookshop.services.BookService;
import org.example.bookshop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ConsoleLineRunner implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public ConsoleLineRunner(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();

//        bookService.printBooksWithReleaseDateAfter(LocalDate.of(2000, 6, 30));
//        authorService.printAllAuthorsWithOneBookAfter1990();
//        authorService.printAllAuthorsOrderedByNumberOfBooks();
//        bookService.printAllBooksFromGeorge();
    }

}
