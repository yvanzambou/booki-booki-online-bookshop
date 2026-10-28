import { Link, NavLink, useNavigate } from "react-router-dom";

export default function SiteLayout({ children, cartCount = 0, user, onLogout }) {
  const navigate = useNavigate();
  return <>
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <div className="container">
        <Link className="navbar-brand fw-bold" to="/books">Booki-Booki</Link>
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav" aria-controls="mainNav" aria-expanded="false" aria-label="Navigation öffnen">
          <span className="navbar-toggler-icon" />
        </button>
        <div className="collapse navbar-collapse" id="mainNav">
          <div className="navbar-nav me-auto">
            <NavLink className="nav-link" to="/books">Bücher</NavLink>
          </div>
          <div className="d-flex align-items-center gap-3">
            <Link className="btn btn-outline-light btn-sm" to="/cart">Warenkorb ({cartCount})</Link>
            {user ? <>
              <span className="text-white small d-none d-md-inline">Hallo, {user.firstName}</span>
              <button className="btn btn-light btn-sm" onClick={async () => { await onLogout(); navigate("/books"); }}>Abmelden</button>
            </> : <Link className="btn btn-light btn-sm" to="/login">Anmelden</Link>}
          </div>
        </div>
      </div>
    </nav>
    <main className="min-vh-100">{children}</main>
    <footer className="bg-light border-top py-4 mt-5">
      <div className="container text-muted small d-flex justify-content-between flex-wrap gap-2">
        <span>© {new Date().getFullYear()} Booki-Booki</span>
        <span>Dein Buchshop – ein Lernprojekt</span>
      </div>
    </footer>
  </>;
}
