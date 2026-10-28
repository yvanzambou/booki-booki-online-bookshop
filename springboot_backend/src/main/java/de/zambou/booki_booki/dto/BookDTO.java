package de.zambou.booki_booki.dto;

import lombok.Data;

import java.util.List;

public record BookDTO(
    Long id,
    String title,
    String author,
    String rating,
    String description,
    String language,
    String isbn,
    List<GenreDTO> genres,
    String bookFormat,
    String edition,
    String pages,
    String publisher,
    String publishDate,
    String firstPublishDate,
    String numRating,
    String coverImg,
    String price) {}
