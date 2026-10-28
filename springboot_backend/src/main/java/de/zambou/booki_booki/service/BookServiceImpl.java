package de.zambou.booki_booki.service;

import de.zambou.booki_booki.dto.BookDTO;
import de.zambou.booki_booki.mapper.BookMapper;
import de.zambou.booki_booki.model.Book;
import de.zambou.booki_booki.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAllBooks().stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getBooksByGenre(String genre) {
        return bookRepository.findBooksByGenre(genre).stream()
                .map(BookMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findByIdWithGenres(id)
                .map(BookMapper::toDTO);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}
