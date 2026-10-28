import { Link } from "react-router-dom";
import { formatEuroDE } from "../api/bookService";

export default function CartPage({ cart, onChangeQuantity, onRemove, user }) {
  const total = cart.reduce((sum, item) => sum + Number(String(item.price ?? 0).replace(",", ".")) * item.quantity, 0);
  return <div className="container py-5">
    <h1 className="h2 fw-bold mb-4">Dein Warenkorb</h1>
    {!cart.length ? <div className="text-center py-5 bg-light rounded-3"><p className="lead">Dein Warenkorb ist noch leer.</p><Link to="/books" className="btn btn-booki-primary">Bücher entdecken</Link></div> :
      <div className="row g-4">
        <div className="col-lg-8">
          {cart.map(item => <div key={item.id} className="card mb-3"><div className="card-body d-flex gap-3 align-items-center">
            <img src={item.coverImg} alt="" style={{ width: 76, height: 105, objectFit: "cover" }} onError={e => { e.currentTarget.style.visibility = "hidden"; }} />
            <div className="flex-grow-1"><Link className="fw-semibold text-dark text-decoration-none" to={`/book/${item.id}`}>{item.title}</Link><div className="text-muted small">{item.author}</div><div className="mt-2 fw-semibold">{formatEuroDE(item.price)}</div></div>
            <div className="d-flex align-items-center gap-2"><button className="btn btn-outline-secondary btn-sm" aria-label="Menge verringern" onClick={() => onChangeQuantity(item.id, item.quantity - 1)}>−</button><span>{item.quantity}</span><button className="btn btn-outline-secondary btn-sm" aria-label="Menge erhöhen" onClick={() => onChangeQuantity(item.id, item.quantity + 1)}>+</button></div>
            <button className="btn btn-link text-danger" onClick={() => onRemove(item.id)}>Entfernen</button>
          </div></div>)}
        </div>
        <div className="col-lg-4"><div className="card"><div className="card-body"><h2 className="h5">Bestellübersicht</h2><div className="d-flex justify-content-between my-3"><span>Zwischensumme</span><strong>{formatEuroDE(total)}</strong></div><div className="d-flex justify-content-between mb-3"><span>Versand</span><span>Im nächsten Schritt</span></div><hr/><Link to={user ? "/checkout" : "/login?next=checkout"} className="btn btn-booki-primary w-100">Zur Kasse</Link><Link to="/books" className="btn btn-booki-secondary w-100 mt-2">Weiter einkaufen</Link></div></div></div>
      </div>}
  </div>;
}
