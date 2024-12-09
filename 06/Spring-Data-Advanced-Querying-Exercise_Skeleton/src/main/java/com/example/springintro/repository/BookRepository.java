package com.example.springintro.repository;

import com.example.springintro.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

//    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);
//
//    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);
//
//    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int maxCopies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal upper);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findAllByReleaseDateBefore(LocalDate before);

    List<Book> findAllByTitleContainingIgnoreCase(String text);

    List<Book> findAllByAuthorLastNameStartingWith(String text);

    @Query("SELECT COUNT(b) FROM Book as b WHERE LENGTH(b.title) > :num")
    int countByTitleMoreThan(int num);

    BookInfo findByTitle(String title);
}
