package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.AuthorCopies;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookInfo;
import org.springframework.data.jpa.repository.Query;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public interface BookService {
    void seedBooks() throws IOException;
//
//    List<Book> findAllBooksAfterYear(int year);
//
//    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);
//
//    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    void printAllBooksByAgeRestriction();

    List<Book> getAllGoldenBooks();

    List<Book> getAllBetweenPriceRange();

    List<Book> getAllNotInGivenYear(Scanner scanner);

    List<Book> getBookTitlesBeforeDate(Scanner scanner);

    List<Book> getBookTitlesContaining(Scanner scanner);

    List<Book> getBooksWithAuthorNameLike(Scanner scanner);

    int getCountWithTitleLettersCount(String input);

    BookInfo findBookInfo(String title);

}
