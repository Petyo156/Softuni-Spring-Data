package org.example.bookshop.persistence.repositories;

import org.example.bookshop.persistence.entities.Author;
import org.example.bookshop.persistence.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findAllByBooksReleaseDateBefore(LocalDate releaseDate);
    List<Author> findAllByOrderByBooksDesc();

}
