package com.alura.literalura.CONTROLLER;

import com.alura.literalura.MODEL.Author;
import com.alura.literalura.MODEL.Book;
import com.alura.literalura.REPOSITORY.AuthorRepository;
import com.alura.literalura.REPOSITORY.BookRepository;
import com.alura.literalura.SERVICE.GutendexService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class LiteraluraController implements CommandLineRunner {

    private final GutendexService gutendexService;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public void run(String... args) {
        try (Scanner scanner = new Scanner(System.in).useDelimiter("\n")) {
            boolean running = true;

            while (running) {
                mostrarMenu();
                int opcion = obtenerOpcion(scanner);

                switch (opcion) {
                    case 1 -> buscarLibro(scanner);
                    case 2 -> listarLibros();
                    case 3 -> listarAutores();
                    case 4 -> listarAutoresVivos(scanner);
                    case 5 -> listarLibrosPorIdioma(scanner);
                    case 0 -> {
                        System.out.println("Saliendo del programa...");
                        running = false;
                    }
                    default -> System.out.println("Opción inválida. Inténtalo de nuevo.");
                }
            }
        }
    }

    private void mostrarMenu() {
        System.out.println("\n********************--- Catálogo LiterAlura ---********************");
        System.out.println("1. Buscar Libro");
        System.out.println("2. Listar Libros");
        System.out.println("3. Listar Autores");
        System.out.println("4. Listar Autores Vivos por Año");
        System.out.println("5. Listar Libros por Idioma");
        System.out.println("0. Salir");
    }

    private int obtenerOpcion(Scanner scanner) {
        System.out.print("Escoge una opción: ");
        try {
            return Integer.parseInt(scanner.next());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Debes ingresar un número.");
            return -1;
        }
    }

    private void buscarLibro(Scanner scanner) {
        System.out.print("Escribe el título del libro: ");
        String titulo = scanner.next();

        Optional<Book> libroApi = gutendexService.searchBookByTitle(titulo);
        libroApi.ifPresentOrElse(libro -> {
            Optional<Book> libroExistente = bookRepository.findByTitleIgnoreCase(libro.getTitle());

            if (libroExistente.isPresent()) {
                System.out.println("⚠️ El libro ya existe en la base de datos. Intenta con otro título.");
                return;
            }

            Author autor = libro.getAuthor();

            Optional<Author> autorExistente = authorRepository
                    .findByNameAndBirthYearAndDeathYear(
                            autor.getName(),
                            autor.getBirthYear(),
                            autor.getDeathYear()
                    );

            if (autorExistente.isPresent()) {
                libro.setAuthor(autorExistente.get());
            } else {
                Author autorGuardado = authorRepository.save(autor);
                libro.setAuthor(autorGuardado);
            }

            bookRepository.save(libro);
            System.out.println("✅ Libro guardado:\n" + libro);

        }, () -> System.out.println("❌ Libro no encontrado."));
    }

    private void listarLibros() {
        List<Book> libros = bookRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            libros.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        List<Author> autores = authorRepository.findAllWithBooks();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return;
        }

        for (Author autor : autores) {
            System.out.println("\n*********************************Autor*********************************");
            System.out.println("Nombre: " + autor.getName());
            System.out.println("Fecha de Nacimiento: " + autor.getBirthYear());
            System.out.println("Fecha de Fallecimiento: " + autor.getDeathYear());

            List<String> titulos = autor.getBooks().stream()
                    .map(Book::getTitle)
                    .distinct()
                    .toList();

            System.out.println("Libros: " + titulos);
        }
    }


    private void listarAutoresVivos(Scanner scanner) {
        System.out.print("Introduce el año para consultar autores vivos: ");
        try {
            int year = Integer.parseInt(scanner.next());
            List<Author> autoresVivos = authorRepository.findLivingAuthorsByYear(year);

            if (autoresVivos.isEmpty()) {
                System.out.println("No hay autores vivos en el año " + year);
            } else {
                autoresVivos.forEach(autor -> {
                    System.out.println("\n*********************************Autor*********************************");
                    System.out.println("Nombre: " + autor.getName());
                    System.out.println("Fecha de Nacimiento: " + autor.getBirthYear());
                    System.out.println("Fecha de Fallecimiento: " + autor.getDeathYear());

                    List<Book> libros = autor.getBooks();
                    if (libros == null || libros.isEmpty()) {
                        System.out.println("Libros: []");
                    } else {
                        String titulos = libros.stream()
                                .map(Book::getTitle)
                                .distinct()
                                .reduce((a, b) -> a + ", " + b)
                                .map(str -> "Libros [" + str + "]")
                                .orElse("Libros []");
                        System.out.println(titulos);
                    }
                });
            }
        } catch (NumberFormatException e) {
            System.out.println("Año inválido. Debes ingresar un número entero.");
        }
    }

    private void listarLibrosPorIdioma(Scanner scanner) {
        System.out.print("Ingresa el código de idioma (por ejemplo: en, es): ");
        String idioma = scanner.next();

        List<Book> libros = bookRepository.findByLanguagesContainingIgnoreCase(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma '" + idioma + "'");
        } else {
            libros.forEach(System.out::println);
        }
    }
}