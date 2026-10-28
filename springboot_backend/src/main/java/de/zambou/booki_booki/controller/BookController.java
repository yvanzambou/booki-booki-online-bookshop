package de.zambou.booki_booki.controller;

import de.zambou.booki_booki.dto.BookDTO;
import de.zambou.booki_booki.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/booki-booki")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/books/{genre}")
    public ResponseEntity<List<BookDTO>> getBooksByGenre(@PathVariable String genre) {
        List<BookDTO> books = bookService.getBooksByGenre(genre);
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Buch nicht gefunden"));
        return ResponseEntity.ok(book);
    }

//    @PostMapping("/add-book")
//    public ResponseEntity<Book> addBook(@RequestBody Book book) {
//        Book addedBook = bookService.saveBook(book);
//        return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
//    }
}
