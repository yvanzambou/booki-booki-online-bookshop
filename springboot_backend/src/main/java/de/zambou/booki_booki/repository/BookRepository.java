package de.zambou.booki_booki.repository;

import de.zambou.booki_booki.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // LEFT JOIN FETCH: auch Bücher ohne Genre werden geliefert
    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.genres")
    List<Book> findAllBooks();

    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.genres WHERE b.id = :id")
    Optional<Book> findByIdWithGenres(@Param("id") Long id);

    @Query("SELECT DISTINCT b FROM Book b JOIN FETCH b.genres WHERE b.id IN " +
            "(SELECT b2.id FROM Book b2 JOIN b2.genres g2 WHERE g2.genre = :genre)")
    List<Book> findBooksByGenre(@Param("genre") String genre);
}
