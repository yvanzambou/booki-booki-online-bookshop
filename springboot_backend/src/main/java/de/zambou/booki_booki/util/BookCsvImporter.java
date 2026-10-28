package de.zambou.booki_booki.util;

import de.zambou.booki_booki.service.BookImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Importiert beim Start die CSV-Datei, aber nur wenn app.csv.import-on-startup=true
 * (Umgebungsvariable CSV_IMPORT_ON_STARTUP) und die Datenbank noch leer ist.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.csv.import-on-startup", havingValue = "true")
public class BookCsvImporter implements CommandLineRunner {

    private final BookImportService bookImportService;

    @Override
    public void run(String... args) {
        int imported = bookImportService.importBooksFromCsv(false);
        log.info("Start-Import abgeschlossen: {} Bücher.", imported);
    }
}
