package com.example.springintro.service;

import com.example.springintro.model.entity.Author;
import com.example.springintro.model.entity.AuthorCopies;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<Author> getNameEndingWith(Scanner scanner);

//    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<AuthorCopies> getCopiesByAuthor();
}
