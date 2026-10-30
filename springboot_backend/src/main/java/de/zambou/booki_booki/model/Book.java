package de.zambou.booki_booki.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String rating;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String language;
    private String isbn;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();

    private String bookFormat;
    private String edition;
    private String pages;
    private String publisher;
    private String publishDate;
    private String firstPublishDate;
    private String numRating;
    private String coverImg;
    private String price;

    public Book() {
    }
}
