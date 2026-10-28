package de.zambou.booki_booki.controller;

import de.zambou.booki_booki.dto.GenreDTO;
import de.zambou.booki_booki.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booki-booki")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public ResponseEntity<List<GenreDTO>> getGenres() {
        List<GenreDTO> genres = genreService.getAllGenres().stream()
                .map(g -> new GenreDTO(g.getId(), g.getGenre()))
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(genres);
    }

//    @PostMapping("/add-genre")
//    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
//        Genre addedGenre = genreService.saveGenre(genre);
//        return ResponseEntity.status(HttpStatus.CREATED).body(addedGenre);
//    }
}
