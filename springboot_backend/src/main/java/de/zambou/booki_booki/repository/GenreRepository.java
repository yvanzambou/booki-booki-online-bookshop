package de.zambou.booki_booki.repository;

import de.zambou.booki_booki.model.Book;
import de.zambou.booki_booki.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByGenre(String genreName);
}
