package com.alura.literalura.MODEL;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "birthYear", "deathYear"}))
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonAlias("birth_year")
    private Integer birthYear;

    @JsonAlias("death_year")
    private Integer deathYear;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

    public Author() {}

    public Author(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author author)) return false;
        return Objects.equals(name, author.name) &&
                Objects.equals(birthYear, author.birthYear) &&
                Objects.equals(deathYear, author.deathYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthYear, deathYear);
    }

    @Override
    public String toString() {
        return "\n*********************************Autor*********************************\n" +
                "Nombre: " + name + "\n" +
                "Fecha de Nacimiento: " + birthYear + "\n" +
                "Fecha de Fallecimiento: " + deathYear + "\n" +
                "Libros: " + (books == null ? "[]" :
                books.stream().map(Book::getTitle).toList()) + "\n";
    }
}