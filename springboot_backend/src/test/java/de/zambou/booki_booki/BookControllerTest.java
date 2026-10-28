package de.zambou.booki_booki;

import de.zambou.booki_booki.model.Book;
import de.zambou.booki_booki.model.Genre;
import de.zambou.booki_booki.repository.BookRepository;
import de.zambou.booki_booki.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    GenreRepository genreRepository;

    private Book saveBook(String title, List<Genre> genres) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor("Autor");
        book.setPrice("9.99");
        book.setGenres(new ArrayList<>(genres));
        return bookRepository.saveAndFlush(book);
    }

    @Test
    void getBookById_returnsBookWithGenres() throws Exception {
        Genre fantasy = new Genre();
        fantasy.setGenre("Fantasy");
        genreRepository.saveAndFlush(fantasy);
        Book book = saveBook("Der Test", List.of(fantasy));

        mockMvc.perform(get("/booki-booki/book/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Der Test"))
                .andExpect(jsonPath("$.genres[0].genre").value("Fantasy"));
    }

    @Test
    void getBookById_unknownId_returns404() throws Exception {
        mockMvc.perform(get("/booki-booki/book/{id}", 999999))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBooks_includesBooksWithoutGenre() throws Exception {
        saveBook("Ohne Genre", List.of());

        mockMvc.perform(get("/booki-booki/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.title == 'Ohne Genre')]").exists());
    }

    @Test
    void getBooksByGenre_filtersByGenre() throws Exception {
        Genre crime = new Genre();
        crime.setGenre("Krimi");
        genreRepository.saveAndFlush(crime);
        saveBook("Krimi-Buch", List.of(crime));
        saveBook("Anderes Buch", List.of());

        mockMvc.perform(get("/booki-booki/books/{genre}", "Krimi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Krimi-Buch"));
    }
}
