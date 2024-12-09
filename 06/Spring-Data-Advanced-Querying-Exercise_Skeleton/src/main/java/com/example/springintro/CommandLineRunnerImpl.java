package com.example.springintro;

import com.example.springintro.model.entity.AuthorCopies;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookInfo;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final Scanner scanner;

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.scanner = new Scanner(System.in);
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

//        printBooks();
//        printAllGoldenBooksTitles();
//        printAllBookTitlesForPriceRange();
//        printAllBookTitlesNotInYear(scanner);
//        printBookTitlesBeforeDate(scanner);
//        printAuthorsNamesLike(scanner);
//        printBookTitlesContaining(scanner);
//        printBookTitlesWithAuthorNameStartingWith(scanner);
//        printCountBooks(scanner);
//        printAllByBookTitle(scanner);
        printNumberOfCopiesByAuthor();
    }

    private void printNumberOfCopiesByAuthor() {
        List<AuthorCopies> copiesByAuthor = authorService.getCopiesByAuthor();
        copiesByAuthor.forEach(
                authorCopies -> {
                    System.out.printf("%s %s %s%n", authorCopies.getFirstName(), authorCopies.getLastName(), authorCopies.getCopyCount());
                }
        );
    }

    private void printAllByBookTitle(Scanner scanner) {
        String title = scanner.nextLine();
        BookInfo bookInfo = bookService.findBookInfo(title);
        System.out.println(bookInfo.getTitle() + " " + bookInfo.getPrice() + " " + bookInfo.getAgeRestriction() + " " + bookInfo.getEditionType());
    }

    private void printCountBooks(Scanner scanner) {
        String input = scanner.nextLine();
        int count = bookService.getCountWithTitleLettersCount(input);
        System.out.printf("There are %d books with longer titles than %d symbols.", count, Integer.parseInt(input));
    }

    private void printBookTitlesWithAuthorNameStartingWith(Scanner scanner) {
        bookService.getBooksWithAuthorNameLike(scanner).forEach(
                book -> System.out.printf("%s - %s %s%n", book.getTitle(), book.getAuthor().getFirstName(), book.getAuthor().getLastName())
        );
    }


    private void printBookTitlesContaining(Scanner scanner) {
        bookService.getBookTitlesContaining(scanner).forEach(
                book -> {
                    System.out.println(book.getTitle());
                }
        );
    }


    private void printAuthorsNamesLike(Scanner scanner) {
        authorService.getNameEndingWith(scanner).forEach(
                a -> System.out.println(a.getFirstName() + " " + a.getLastName())
        );
    }

    private void printBookTitlesBeforeDate(Scanner scanner) {
        bookService.getBookTitlesBeforeDate(scanner).forEach(
                book -> System.out.printf("%s %s %s%n", book.getTitle(), book.getEditionType().name(), book.getPrice().toString())
        );
    }

    private void printBooks() {
        bookService.printAllBooksByAgeRestriction();
    }

    private void printAllGoldenBooksTitles() {
        bookService.getAllGoldenBooks().forEach(
                book -> {
                    System.out.println(book.getTitle());
                }
        );
    }

    private void printAllBookTitlesForPriceRange() {
        bookService.getAllBetweenPriceRange().forEach(
                book -> {
                    System.out.printf("%s - $%s%n", book.getTitle(), book.getPrice().toString());
                }
        );
    }

    private void printAllBookTitlesNotInYear(Scanner scanner) {
        bookService.getAllNotInGivenYear(scanner).forEach(
                book -> {
                    System.out.println(book.getTitle());
                }
        );
    }
//
//    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
//        bookService
//                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
//                .forEach(System.out::println);
//    }
//
//    private void printAllAuthorsAndNumberOfTheirBooks() {
//        authorService
//                .getAllAuthorsOrderByCountOfTheirBooks()
//                .forEach(System.out::println);
//    }

//    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
//        bookService
//                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
//                .forEach(System.out::println);
//    }
//
//    private void printAllBooksAfterYear(int year) {
//        bookService
//                .findAllBooksAfterYear(year)
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);
//    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
