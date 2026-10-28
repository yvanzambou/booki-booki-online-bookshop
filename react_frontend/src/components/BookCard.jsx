import { Link } from "react-router-dom";
import { formatEuroDE } from "../api/bookService";

export default function BookCard({ book }) {
  return (
    <Link
      to={`/book/${book.id}`}
      className="text-decoration-none text-dark"
      style={{ cursor: "pointer" }}
    >
      <div className="card book-card h-100 shadow-sm border-1">
        
        {/* Cover */}
        <div className="d-flex justify-content-center pt-3">
          <img
            src={book.coverImg}
            alt={book.title}
            className="img-fluid"
            style={{
              height: "260px",
              width: "180px",
              objectFit: "cover",
              borderRadius: "6px"
            }}
          />
        </div>

        {/* Inhalt */}
        <div className="card-body text-center">
          <h5 className="card-title mb-2">{book.title}</h5>

          <p className="text-muted small mb-2">{book.author}</p>

          <p className="fw-semibold mt-2">
            {formatEuroDE(book.price)}
          </p>
        </div>

      </div>
    </Link>
  );
}