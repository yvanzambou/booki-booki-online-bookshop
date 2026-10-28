package de.zambou.booki_booki.controller;

import de.zambou.booki_booki.service.BookImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booki-booki")
@RequiredArgsConstructor
public class BookCsvController {

    private final BookImportService bookImportService;

    /**
     * Startet den CSV-Import (Pfad aus app.csv.path).
     * Ohne ?force=true wird nur importiert, wenn noch keine Bücher in der DB sind.
     */
    @PostMapping("/csv-import")
    public ResponseEntity<String> importCsv(@RequestParam(defaultValue = "false") boolean force) {
        if (!bookImportService.csvFileExists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("CSV-Datei nicht gefunden: " + bookImportService.getCsvPath());
        }
        int imported = bookImportService.importBooksFromCsv(force);
        return ResponseEntity.ok(imported + " Bücher importiert");
    }
}
