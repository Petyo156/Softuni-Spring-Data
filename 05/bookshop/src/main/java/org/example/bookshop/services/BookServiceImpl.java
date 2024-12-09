package org.example.bookshop.services;

import org.example.bookshop.persistence.entities.Author;
import org.example.bookshop.persistence.entities.Book;
import org.example.bookshop.persistence.entities.Category;
import org.example.bookshop.persistence.entities.enums.AgeRestriction;
import org.example.bookshop.persistence.entities.enums.EditionType;
import org.example.bookshop.persistence.repositories.AuthorRepository;
import org.example.bookshop.persistence.repositories.BookRepository;
import org.example.bookshop.persistence.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_PATH = "src/main/resources/files/books.txt";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedBooks() throws IOException {
        if (!areBooksImported()) {
            Set<Book> books = new HashSet<>();
            Files.readAllLines(Path.of(BOOKS_PATH)).forEach(line -> {
                String[] tokens = line.split("\\s+");
                Book book = new Book(
                        AgeRestriction.values()[Integer.parseInt(tokens[4])],
                        Integer.parseInt(tokens[2]),
                        EditionType.values()[Integer.parseInt(tokens[0])],
                        LocalDate.parse(tokens[1], DateTimeFormatter.ofPattern("d/M/yyyy")),
                        new BigDecimal(tokens[3]),
                        Arrays.stream(tokens).skip(5).collect(Collectors.joining(" "))
                );
                Author author = getRandomAuthor();
                book.setAuthor(author);

                Set<Category> categories = getRandomCategories();
                book.setCategories(categories);

                books.add(book);
            });
            bookRepository.saveAll(books);
        }
        System.out.println("Count books: " + this.bookRepository.count());
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        long count = ThreadLocalRandom.current().nextLong(1, 4);

        for (int i = 0; i < count; i++) {
            long idCategory = ThreadLocalRandom.current().nextLong(1, this.categoryRepository.count() + 1);
            categories.add(this.categoryRepository.findById(idCategory).get());
        }

        return categories;
    }

    private Author getRandomAuthor() {
        long i = ThreadLocalRandom.current().nextLong(1, this.authorRepository.count() + 1);
        Optional<Author> author = authorRepository.findById(i);
        return author.orElse(null);
    }

    @Override
    public boolean areBooksImported() {
        return bookRepository.count() > 0;
    }

    @Override
    public void printBooksWithReleaseDateAfter(LocalDate date) {
        List<Book> byReleaseDateAfter = bookRepository.findByReleaseDateAfter(date);
        byReleaseDateAfter.forEach(book -> {
            System.out.printf("Book name: %s, Author: %s%n", book.getTitle(),
                    book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName());
        });
    }

    @Override
    public void printAllBooksFromGeorge() {
        bookRepository.findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDesc("George", "Powell")
                .forEach(a -> System.out.printf("Book name: %s, Author: %s%n", a.getTitle(), a.getAuthor().getFirstName() + " " + a.getAuthor().getLastName()));
    }


}
