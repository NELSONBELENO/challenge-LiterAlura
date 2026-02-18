package com.alura.literalura.MODEL;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "books")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    private String languages;

    @Column(name = "download_count")
    @JsonAlias("download_count")
    private Integer downloadCount;

    public Book() {}

    public Book(String title, Author author, String languages, Integer downloadCount) {
        this.title = title;
        this.author = author;
        this.languages = languages;
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return "\n**********************************Libros**********************************:\n" +
                "Titulo: " + title + "\n" +
                "Autor: " + author.getName() + "\n" +
                "Lenguaje: " + languages + "\n" +
                "Contador de Descargas: " + downloadCount + "\n";
    }
}