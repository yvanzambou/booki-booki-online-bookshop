package de.zambou.booki_booki.mapper;

import de.zambou.booki_booki.dto.BookDTO;
import de.zambou.booki_booki.dto.GenreDTO;
import de.zambou.booki_booki.model.Book;

import java.util.List;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        List<GenreDTO> genres = book.getGenres()
                .stream()
                .map(g -> new GenreDTO(g.getId(), g.getGenre()))
                .toList();

        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getRating(),
                book.getDescription(),
                book.getLanguage(),
                book.getIsbn(),
                genres,
                book.getBookFormat(),
                book.getEdition(),
                book.getPages(),
                book.getPublisher(),
                book.getPublishDate(),
                book.getFirstPublishDate(),
                book.getNumRating(),
                book.getCoverImg(),
                book.getPrice()
        );
    }
}
