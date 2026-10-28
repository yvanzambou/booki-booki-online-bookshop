package de.zambou.booki_booki.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import de.zambou.booki_booki.model.Book;
import de.zambou.booki_booki.model.Genre;
import de.zambou.booki_booki.repository.BookRepository;
import de.zambou.booki_booki.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookImportService {

    /** Höchster benötigter Spaltenindex (price = row[17]) -> mindestens 18 Spalten. */
    private static final int MIN_COLUMNS = 18;
    private static final int BATCH_SIZE = 100;

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    /** Pfad zur CSV-Datei (Property app.csv.path / Umgebungsvariable CSV_PATH). */
    @Value("${app.csv.path}")
    private String csvPath;

    /** Maximale Anzahl zu importierender Bücher (Property app.csv.max-books). */
    @Value("${app.csv.max-books:100}")
    private int maxBooks;

    public Path getCsvPath() {
        return Path.of(csvPath);
    }

    public boolean csvFileExists() {
        return Files.isRegularFile(getCsvPath());
    }

    /**
     * Importiert Bücher aus der konfigurierten CSV-Datei.
     * Ohne force wird nichts importiert, wenn die Tabelle bereits Bücher enthält
     * (verhindert doppelte Einträge).
     *
     * @return Anzahl importierter Bücher
     */
    @Transactional
    public int importBooksFromCsv(boolean force) {
        if (!force && bookRepository.count() > 0) {
            log.info("CSV-Import übersprungen: Datenbank enthält bereits Bücher.");
            return 0;
        }
        if (!csvFileExists()) {
            log.warn("CSV-Import übersprungen: Datei nicht gefunden: {}", csvPath);
            return 0;
        }

        int imported = 0;
        Map<String, Genre> genreCache = new HashMap<>();

        try (Reader fileReader = Files.newBufferedReader(getCsvPath(), StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(fileReader)) {

            reader.readNext(); // Kopfzeile überspringen

            List<Book> books = new ArrayList<>();
            String[] row;

            while ((row = reader.readNext()) != null) {
                if (row.length < MIN_COLUMNS) continue;
                if (imported >= maxBooks) break;

                try {
                    Book book = new Book();
                    book.setTitle(row[2]);
                    book.setAuthor(row[3]);
                    book.setRating(row[4]);
                    book.setDescription(row[5]);
                    book.setLanguage(row[6]);
                    book.setIsbn(row[7]);
                    book.setGenres(parseGenres(row[8], genreCache));
                    book.setBookFormat(row[9]);
                    book.setEdition(row[10]);
                    book.setPages(row[11]);
                    book.setPublisher(row[12]);
                    book.setPublishDate(row[13]);
                    book.setFirstPublishDate(row[14]);
                    book.setNumRating(row[15]);
                    book.setCoverImg(row[16]);
                    book.setPrice(row[17]);

                    books.add(book);
                    imported++;

                    if (books.size() == BATCH_SIZE) {
                        bookRepository.saveAll(books);
                        books.clear();
                    }
                } catch (Exception e) {
                    log.warn("Fehler bei Buch Nr. {}: {}", imported + 1, e.getMessage());
                }
            }

            if (!books.isEmpty()) {
                bookRepository.saveAll(books);
            }

        } catch (IOException | CsvException e) {
            log.error("Fehler beim Lesen der CSV-Datei {}: {}", csvPath, e.getMessage());
            throw new IllegalStateException("CSV-Import fehlgeschlagen: " + e.getMessage(), e);
        }

        log.info("{} Bücher importiert.", imported);
        return imported;
    }

    private List<Genre> parseGenres(String raw, Map<String, Genre> genreCache) {
        if (raw == null || raw.isBlank()) return new ArrayList<>();

        List<String> names = Arrays.stream(raw
                        .replace("[", "")
                        .replace("]", "")
                        .replace("'", "")
                        .split(","))
                .map(String::trim)
                .filter(str -> !str.isEmpty())
                .distinct()
                .toList();

        List<Genre> genres = new ArrayList<>();
        for (String name : names) {
            Genre genre = genreCache.computeIfAbsent(name, n ->
                    genreRepository.findByGenre(n)
                            .orElseGet(() -> {
                                Genre g = new Genre();
                                g.setGenre(n);
                                return genreRepository.save(g);
                            })
            );
            genres.add(genre);
        }
        return genres;
    }
}
