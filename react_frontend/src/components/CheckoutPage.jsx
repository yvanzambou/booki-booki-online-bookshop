import {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {formatEuroDE, getPaymentProfile, savePaymentProfile} from "../api/bookService";

const empty = {
    recipient: "",
    street: "",
    postalCode: "",
    city: "",
    country: "Deutschland",
    paymentMethod: "PAYPAL",
    paymentEmail: ""
};
export default function CheckoutPage({cart, user, token, clearCart}) {
    const [form, setForm] = useState(empty);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState(false);
    const [busy, setBusy] = useState(false);
    const navigate = useNavigate();
    useEffect(() => {
        if (!token) {
            navigate("/login");
            return;
        }
        getPaymentProfile(token).then(p => {
            if (p) setForm(p);
        }).catch(() => {
        });
    }, [token, navigate]);
    const total = cart.reduce((sum, item) => sum + Number(String(item.price ?? 0).replace(",", ".")) * item.quantity, 0);

    function update(e) {
        setForm({...form, [e.target.name]: e.target.value});
    }

    async function submit(e) {
        e.preventDefault();
        setError("");
        setSuccess(false);
        setBusy(true);
        try {
            await savePaymentProfile(token, form);
            setSuccess(true);
            // This demo saves address and preferred payment method. It does not charge a payment or create a real order.
        } catch (err) {
            setError(err.response?.data?.detail || err.response?.data?.message || "Daten konnten nicht gespeichert werden.");
        } finally {
            setBusy(false);
        }
    }

    if (!cart.length) return <div className="container py-5"><h1 className="h3">Dein Warenkorb ist leer</h1><Link
        to="/books">Zurück zu den Büchern</Link></div>;
    return <div className="container py-5">
        <h1 className="h2 fw-bold mb-2">Kasse</h1><p className="text-muted mb-4">Hallo {user?.firstName}, gib deine
        Rechnungsdaten und bevorzugte Zahlungsart ein.</p>
        {success &&
            <div className="alert alert-success">Deine Angaben wurden gespeichert. Eine Zahlung oder verbindliche
                Bestellung wurde noch nicht ausgelöst.</div>}
        {error && <div className="alert alert-danger">{error}</div>}
        <div className="row g-4">
            <div className="col-lg-7">
                <form className="card" onSubmit={submit}>
                    <div className="card-body p-4">
                        <h2 className="h5 mb-3">Rechnungs- / Lieferadresse</h2>
                        <div className="mb-3"><label className="form-label" htmlFor="recipient">Name des
                            Empfängers</label><input id="recipient" name="recipient" className="form-control"
                                                     value={form.recipient} onChange={update} required maxLength="120"/>
                        </div>
                        <div className="mb-3"><label className="form-label" htmlFor="street">Straße und
                            Hausnummer</label><input id="street" name="street" className="form-control"
                                                     value={form.street} onChange={update} required maxLength="180"
                                                     autoComplete="street-address"/></div>
                        <div className="row">
                            <div className="col-sm-4 mb-3"><label className="form-label"
                                                                  htmlFor="postalCode">PLZ</label><input id="postalCode"
                                                                                                         name="postalCode"
                                                                                                         className="form-control"
                                                                                                         value={form.postalCode}
                                                                                                         onChange={update}
                                                                                                         required
                                                                                                         maxLength="20"
                                                                                                         autoComplete="postal-code"/>
                            </div>
                            <div className="col-sm-8 mb-3"><label className="form-label"
                                                                  htmlFor="city">Ort</label><input id="city" name="city"
                                                                                                   className="form-control"
                                                                                                   value={form.city}
                                                                                                   onChange={update}
                                                                                                   required
                                                                                                   maxLength="100"
                                                                                                   autoComplete="address-level2"/>
                            </div>
                        </div>
                        <div className="mb-4"><label className="form-label" htmlFor="country">Land</label><select
                            id="country" name="country" className="form-select" value={form.country} onChange={update}>
                            <option>Deutschland</option>
                            <option>Österreich</option>
                            <option>Schweiz</option>
                            <option>Frankreich</option>
                            <option>Sonstiges</option>
                        </select></div>
                        <h2 className="h5 mb-3">Zahlungsart</h2>
                        <div className="mb-3"><label className="form-label" htmlFor="paymentMethod">Bevorzugte
                            Zahlungsart</label><select id="paymentMethod" name="paymentMethod" className="form-select"
                                                       value={form.paymentMethod} onChange={update}>
                            <option value="PAYPAL">PayPal (Demo)</option>
                            <option value="INVOICE">Rechnung (Demo)</option>
                            <option value="CARD_PROVIDER">Kreditkarte über Zahlungsdienstleister (noch nicht
                                angebunden)
                            </option>
                        </select></div>
                        {form.paymentMethod === "PAYPAL" &&
                            <div className="mb-4"><label className="form-label" htmlFor="paymentEmail">PayPal-E-Mail
                                (optional)</label><input id="paymentEmail" name="paymentEmail" type="email"
                                                         className="form-control" value={form.paymentEmail || ""}
                                                         onChange={update} maxLength="120"/></div>}
                        <button className="btn btn-dark w-100"
                                disabled={busy}>{busy ? "Speichert…" : "Angaben speichern"}</button>
                        <p className="form-text mt-3 mb-0">Demo: Es werden keine Zahlungen eingezogen. Speichere hier
                            keine Kartennummer oder Kartenprüfnummer.</p>
                    </div>
                </form>
            </div>
            <div className="col-lg-5">
                <div className="card">
                    <div className="card-body p-4"><h2 className="h5">Deine Bücher</h2>{cart.map(item => <div
                        key={item.id} className="d-flex justify-content-between gap-3 py-2 border-bottom">
                        <span>{item.quantity} × {item.title}</span><strong>{formatEuroDE(Number(String(item.price ?? 0).replace(",", ".")) * item.quantity)}</strong>
                    </div>)}
                        <div className="d-flex justify-content-between mt-3 fs-5">
                            <strong>Gesamt</strong><strong>{formatEuroDE(total)}</strong></div>
                        <div className="alert alert-warning small mt-3 mb-0">Eine echte Bestellung mit Bestellnummer und
                            Zahlungsabwicklung ist noch nicht implementiert.
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>;
}
