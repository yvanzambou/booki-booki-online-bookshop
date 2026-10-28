import { useEffect, useState } from "react";
import { getAllBooks, getAllGenres, getBooksByGenre } from "../api/bookService";
import BookCard from "./BookCard";

export default function BookList() {
  const [books, setBooks] = useState([]);
  const [genres, setGenres] = useState([]);
  const [selectedGenre, setSelectedGenre] = useState("");
  const [search, setSearch] = useState("");
  const [loadingBooks, setLoadingBooks] = useState(false);

  // Genres einmal laden
  useEffect(() => {
    getAllGenres().then(setGenres);
  }, []);

  // Bücher neu laden, wenn Genre sich ändert
  useEffect(() => {
    loadBooks(selectedGenre);
  }, [selectedGenre]);

  function handleGenreChange(ev) {
    setSelectedGenre(ev.target.value);
  }

  function loadBooks(genre = "") {
    setLoadingBooks(true);

    // WICHTIG: fetchFn als Funktion, nicht als Ergebnis!
    const fetchFn = genre ? () => getBooksByGenre(genre) : () => getAllBooks();

    fetchFn()
      .then(setBooks)
      .catch(err => console.error("Fehler beim Laden der Bücher:", err))
      .finally(() => setLoadingBooks(false));
  }

  const filteredBooks = books.filter(book =>
    book.title.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="container py-4">

      {/* Header */}
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2 className="fw-bold m-0">
          {selectedGenre === "" ? "Alle Bücher" : selectedGenre}
        </h2>
        <span className="badge bg-primary fs-5 px-3 py-2">
          {filteredBooks.length} Bücher
        </span>
      </div>

      {/* Filter Card */}
      <div className="card shadow-sm mb-4">
        <div className="card-body d-flex flex-wrap gap-4 align-items-center">

          {/* Genre Auswahl */}
          <div className="d-flex flex-column">
            <label htmlFor="genreSelect" className="fw-semibold mb-1">
              Genre
            </label>
            <select
              id="genreSelect"
              className="form-select"
              value={selectedGenre}
              onChange={handleGenreChange}
              style={{ minWidth: "200px" }}
            >
              <option value="">Alle Genres</option>
              {genres.map((g) => (
                <option key={g.id} value={g.genre}>
                  {g.genre}
                </option>
              ))}
            </select>
          </div>

          {/* Suchfeld */}
          <div className="d-flex flex-column">
            <label htmlFor="nameSearch" className="fw-semibold mb-1">
              Buchtitel
            </label>
            <input
              id="nameSearch"
              type="text"
              className="form-control"
              placeholder="Buchtitel suchen..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              style={{ minWidth: "250px" }}
            />
          </div>

        </div>
      </div>

      {/* Bücherliste */}
      {loadingBooks ? (
        <div className="d-flex justify-content-center align-items-center" style={{ minHeight: "250px" }}>
          <div className="spinner-border text-primary" />
        </div>
      ) : filteredBooks.length === 0 ? (
        <p className="text-muted">Keine Bücher gefunden.</p>
      ) : (
        <div className="row g-4">
          {filteredBooks.map(book => (
            <div key={book.id} className="col-12 col-sm-6 col-md-4 col-lg-3">
              <BookCard book={book} />
            </div>
          ))}
        </div>
      )}
    </div>
  );
}