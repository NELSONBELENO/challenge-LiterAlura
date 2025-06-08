package com.alura.literalura.SERVICE;

import com.alura.literalura.MODEL.Author;
import com.alura.literalura.MODEL.Book;
import com.alura.literalura.REPOSITORY.AuthorRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class GutendexService {
    private static final String BASE_URL = "https://gutendex.com/books/";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final AuthorRepository authorRepository;
    private static final Logger logger = LoggerFactory.getLogger(GutendexService.class);

    public GutendexService(AuthorRepository authorRepository) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.authorRepository = authorRepository;
    }

    public Optional<Book> searchBookByTitle(String title) {
        try {
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "?search=" + encodedTitle))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                JsonNode results = root.path("results");

                if (!results.isEmpty()) {
                    JsonNode bookData = results.get(0);

                    String bookTitle = bookData.path("title").asText();
                    JsonNode authorsNode = bookData.path("authors");

                    if (!authorsNode.isEmpty()) {
                        JsonNode authorData = authorsNode.get(0);
                        String authorName = authorData.path("name").asText();
                        Integer birthYear = authorData.path("birth_year").asInt();
                        Integer deathYear = authorData.path("death_year").asInt();

                        Optional<Author> optionalAuthor = authorRepository
                                .findByNameAndBirthYearAndDeathYear(authorName, birthYear, deathYear);

                        Author author = optionalAuthor.orElseGet(() -> new Author(authorName, birthYear, deathYear));

                        JsonNode languagesNode = bookData.path("languages");
                        String language = languagesNode.isEmpty() ? "Unknown" : languagesNode.get(0).asText();

                        Integer downloadCount = bookData.path("download_count").asInt();

                        Book book = new Book(bookTitle, author, language, downloadCount);
                        return Optional.of(book);
                    }
                }
            } else {
                logger.error("API request failed with status code: {}", response.statusCode());
            }
        } catch (Exception e) {
            logger.error("Error searching for book", e);
        }
        return Optional.empty();
    }
}