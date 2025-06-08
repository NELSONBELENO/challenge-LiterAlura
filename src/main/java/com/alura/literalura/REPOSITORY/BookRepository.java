package com.alura.literalura.REPOSITORY;

import com.alura.literalura.MODEL.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    @NonNull
    @Query("SELECT b FROM Book b JOIN FETCH b.author")
    List<Book> findAll();

    Optional<Book> findByTitleIgnoreCase(String title);

    @Query("SELECT b FROM Book b JOIN FETCH b.author WHERE LOWER(b.languages) LIKE LOWER(CONCAT('%', :language, '%'))")
    List<Book> findByLanguagesContainingIgnoreCase(String language);
}