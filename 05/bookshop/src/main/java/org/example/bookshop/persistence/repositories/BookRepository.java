package org.example.bookshop.persistence.repositories;

import org.example.bookshop.persistence.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByReleaseDateAfter(LocalDate releaseDate);
    List<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDesc(String firstName, String lastName);
}
