package org.example.bookshop.services;

import org.example.bookshop.persistence.entities.Author;
import org.example.bookshop.persistence.entities.Book;
import org.example.bookshop.persistence.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String AUTHORS_PATH = "src/main/resources/files/authors.txt";
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (!hasAuthors()) {
            Set<Author> authors = new HashSet<>();
            Files.readAllLines(Path.of(AUTHORS_PATH)).forEach(line -> {
                String[] split = line.split(" ");
                Author author = new Author(split[0], split[1]);
                authors.add(author);
            });
            authorRepository.saveAll(authors);
        }
        System.out.println("Count authors: " + this.authorRepository.count());
    }

    @Override
    public boolean hasAuthors() {
        return authorRepository.count() > 0;
    }

    @Override
    public void printAllAuthorsWithOneBookAfter1990() {
        List<Author> allByBooksReleaseDateBefore = authorRepository.findAllByBooksReleaseDateBefore(LocalDate.of(1990, 1, 1));
        allByBooksReleaseDateBefore.forEach(author -> {
            System.out.printf("%s %s %d%n", author.getFirstName(), author.getLastName(), (long) author.getBook().size());
        });
    }

    @Override
    public void printAllAuthorsOrderedByNumberOfBooks() {
        this.authorRepository.findAllByBooksReleaseDateBefore(LocalDate.of(1990, 1, 1))
                .stream()
                .sorted((a, b) -> b.getBook().size() > a.getBook().size() ? 1 : -1)
                .forEach(author -> System.out.printf("%s %s %d%n", author.getFirstName(), author.getLastName(), (long) author.getBook().size()));
    }
}
