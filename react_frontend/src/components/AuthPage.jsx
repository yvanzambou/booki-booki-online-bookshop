import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { loginCustomer, registerCustomer } from "../api/bookService";

export default function AuthPage({ mode, onAuthenticated }) {
  const isRegister = mode === "register";
  const navigate = useNavigate();
  const [form, setForm] = useState({ firstName: "", lastName: "", email: "", password: "" });
  const [error, setError] = useState("");
  const [busy, setBusy] = useState(false);
  function update(e) { setForm({ ...form, [e.target.name]: e.target.value }); }
  async function submit(e) {
    e.preventDefault(); setError(""); setBusy(true);
    try {
      const result = isRegister ? await registerCustomer(form) : await loginCustomer({ email: form.email, password: form.password });
      onAuthenticated(result);
      navigate("/books");
    } catch (err) {
      setError(err.response?.data?.detail || err.response?.data?.message || "Die Anfrage ist fehlgeschlagen. Bitte überprüfe deine Angaben.");
    } finally { setBusy(false); }
  }
  return <div className="container py-5" style={{ maxWidth: 560 }}>
    <div className="card border-0 shadow-sm">
      <div className="card-body p-4 p-md-5">
        <h1 className="h3 fw-bold mb-2">{isRegister ? "Konto erstellen" : "Willkommen zurück"}</h1>
        <p className="text-muted mb-4">{isRegister ? "Registriere dich, um deinen Einkauf fortzusetzen." : "Melde dich bei deinem Booki-Booki-Konto an."}</p>
        {error && <div className="alert alert-danger" role="alert">{error}</div>}
        <form onSubmit={submit}>
          {isRegister && <div className="row">
            <div className="col-sm-6 mb-3"><label className="form-label" htmlFor="firstName">Vorname</label><input className="form-control" id="firstName" name="firstName" autoComplete="given-name" value={form.firstName} onChange={update} required maxLength="80" /></div>
            <div className="col-sm-6 mb-3"><label className="form-label" htmlFor="lastName">Nachname</label><input className="form-control" id="lastName" name="lastName" autoComplete="family-name" value={form.lastName} onChange={update} required maxLength="80" /></div>
          </div>}
          <div className="mb-3"><label className="form-label" htmlFor="email">E-Mail-Adresse</label><input className="form-control" id="email" name="email" type="email" autoComplete="email" value={form.email} onChange={update} required maxLength="180" /></div>
          <div className="mb-4"><label className="form-label" htmlFor="password">Passwort</label><input className="form-control" id="password" name="password" type="password" autoComplete={isRegister ? "new-password" : "current-password"} value={form.password} onChange={update} required minLength={isRegister ? 8 : 1} maxLength="72" />{isRegister && <div className="form-text">Mindestens 8 Zeichen.</div>}</div>
          <button className="btn btn-booki-primary w-100" disabled={busy}>{busy ? "Bitte warten…" : isRegister ? "Kostenlos registrieren" : "Anmelden"}</button>
        </form>
        <p className="text-center text-muted mt-4 mb-0">
          {isRegister ? "Du hast bereits ein Konto? " : "Noch kein Konto? "}
          <Link to={isRegister ? "/login" : "/register"}>{isRegister ? "Anmelden" : "Jetzt registrieren"}</Link>
        </p>
      </div>
    </div>
  </div>;
}
