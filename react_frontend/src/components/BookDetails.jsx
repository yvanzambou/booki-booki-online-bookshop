import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { getBookById, formatEuroDE } from "../api/bookService";

export default function BookDetails({ onAddToCart }) {
  const { id } = useParams();
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [added, setAdded] = useState(false);

  useEffect(() => {
    setLoading(true);
    getBookById(id)
      .then(data => setBook(data))
      .catch(err => { console.error("Fehler beim Laden des Buches:", err); setError("Das Buch konnte nicht geladen werden."); })
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) return <div className="detail-loading"><div className="spinner-border text-primary" role="status" /><span className="visually-hidden">Buch wird geladen…</span></div>;
  if (!book) return <div className="container py-5"><p className="text-muted">{error || "Buch nicht gefunden."}</p><Link to="/books" className="btn btn-booki-secondary">Zur Bücherübersicht</Link></div>;

  return <div className="container detail-page py-4 py-lg-5">
    <Link to="/books" className="back-link">← Zurück zur Bücherwelt</Link>
    <header className="detail-heading mt-4 mb-4">
      <span className="eyebrow">ENTDECKE DEIN NÄCHSTES LIEBLINGSBUCH</span>
      <h1>{book.title}</h1>
      <p>von <strong>{book.author}</strong></p>
    </header>
    <div className="detail-layout">
      <aside className="detail-cover-panel">
        {book.coverImg ? <img src={book.coverImg} alt={`Buchcover: ${book.title}`} className="detail-cover" onError={e => { e.currentTarget.style.display = "none"; }} /> : <div className="cover-placeholder">BOOKI<br />BOOKI</div>}
        <div className="detail-cover-caption"><span>✦</span> Ein Buch für deine Sammlung</div>
      </aside>
      <section className="detail-content">
        <div className="detail-badges">{(book.genres || []).map(g => <span key={g.id} className="genre-chip">{g.genre}</span>)}</div>
        <h2>Über dieses Buch</h2>
        <p className="detail-description">{book.description || "Lass dich von dieser Geschichte überraschen und entdecke neue Perspektiven auf jeder Seite."}</p>
        <div className="detail-facts">
          {book.publisher && <div><span>Verlag</span><strong>{book.publisher}</strong></div>}
          {book.publishDate && <div><span>Veröffentlicht</span><strong>{book.publishDate}</strong></div>}
          {book.pages && <div><span>Seiten</span><strong>{book.pages}</strong></div>}
          {book.language && <div><span>Sprache</span><strong>{book.language}</strong></div>}
          {book.isbn && <div><span>ISBN</span><strong>{book.isbn}</strong></div>}
          {book.bookFormat && <div><span>Format</span><strong>{book.bookFormat}</strong></div>}
        </div>
        <div className="detail-buy-panel">
          <div><span className="price-label">Dein Preis</span><div className="detail-price">{formatEuroDE(book.price)}</div><span className="availability"><span /> Sofort in deinen Warenkorb</span></div>
          <button className={`btn btn-booki-primary btn-lg ${added ? "added-button" : ""}`} onClick={() => { onAddToCart?.(book); setAdded(true); window.setTimeout(() => setAdded(false), 1400); }}>
            {added ? "✓ Im Warenkorb" : "＋ In den Warenkorb"}
          </button>
        </div>
      </section>
    </div>
  </div>;
}
