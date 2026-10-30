package de.zambou.booki_booki.service;

import de.zambou.booki_booki.dto.BookDTO;
import de.zambou.booki_booki.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<BookDTO> getAllBooks();

    List<BookDTO> getBooksByGenre(String genre);

    Optional<BookDTO> getBookById(Long id);

    Book saveBook(Book book);
}
