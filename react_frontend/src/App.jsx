import { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.min.js";
import "./App.css";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import BookList from "./components/BookList.jsx";
import BookDetails from "./components/BookDetails.jsx";
import AuthPage from "./components/AuthPage.jsx";
import SiteLayout from "./components/SiteLayout.jsx";
import CartPage from "./components/CartPage.jsx";
import CheckoutPage from "./components/CheckoutPage.jsx";
import { logoutCustomer, getSavedCart, saveCart } from "./api/bookService";
import WelcomePage from "./components/WelcomePage.jsx";

function AppRoutes() {
  const [cart, setCart] = useState(() => {
    try { return JSON.parse(localStorage.getItem("booki-cart") || "[]"); } catch { return []; }
  });
  const [auth, setAuth] = useState(() => {
    try { return JSON.parse(localStorage.getItem("booki-auth") || "null"); } catch { return null; }
  });
  const [cartReady, setCartReady] = useState(false);
  const [loadedCartToken, setLoadedCartToken] = useState(null);
  useEffect(() => { if (!auth) localStorage.setItem("booki-cart", JSON.stringify(cart)); }, [cart, auth]);
  useEffect(() => {
    let active = true;
    if (!auth?.token) { setLoadedCartToken(null); setCartReady(true); return () => { active = false; }; }
    setCartReady(false);
    getSavedCart(auth.token).then(serverLines => {
      if (!active) return;
      const guestCart = (() => { try { return JSON.parse(localStorage.getItem("booki-cart") || "[]"); } catch { return []; } })();
      const combined = new Map();
      serverLines.forEach(item => combined.set(item.id, { ...item, quantity: item.quantity || 1 }));
      guestCart.forEach(item => {
        const old = combined.get(item.id);
        combined.set(item.id, { ...(old || item), quantity: (old?.quantity || 0) + (item.quantity || 1) });
      });
      const merged = Array.from(combined.values());
      setCart(merged);
      localStorage.removeItem("booki-cart");
      setLoadedCartToken(auth.token);
      saveCart(auth.token, merged).catch(err => console.error("Warenkorb konnte nicht gespeichert werden:", err));
    }).catch(err => {
      console.error("Gespeicherter Warenkorb konnte nicht geladen werden:", err);
    }).finally(() => { if (active) setCartReady(true); });
    return () => { active = false; };
  }, [auth?.token]);
  useEffect(() => {
    if (!auth?.token || !cartReady || loadedCartToken !== auth.token) return;
    saveCart(auth.token, cart).catch(err => console.error("Warenkorb konnte nicht gespeichert werden:", err));
  }, [cart, auth?.token, cartReady, loadedCartToken]);
  useEffect(() => { if (auth) localStorage.setItem("booki-auth", JSON.stringify(auth)); else localStorage.removeItem("booki-auth"); }, [auth]);

  function addToCart(book) {
    setCart(previous => {
      const found = previous.find(item => item.id === book.id);
      return found ? previous.map(item => item.id === book.id ? { ...item, quantity: item.quantity + 1 } : item)
        : [...previous, { ...book, quantity: 1 }];
    });
  }
  function changeQuantity(id, quantity) {
    if (quantity < 1) { setCart(previous => previous.filter(item => item.id !== id)); return; }
    setCart(previous => previous.map(item => item.id === id ? { ...item, quantity } : item));
  }
  function removeFromCart(id) { setCart(previous => previous.filter(item => item.id !== id)); }
  async function logout() {
    try { if (auth?.token) await logoutCustomer(auth.token); } catch { /* Lokalen Login auch bei Netzfehler entfernen. */ }
    setAuth(null);
    setCart([]);
    setCartReady(true);
  }
  const user = auth ? { id: auth.id, firstName: auth.firstName, lastName: auth.lastName, email: auth.email } : null;
  const layout = page => <SiteLayout cartCount={cart.reduce((n, item) => n + item.quantity, 0)} user={user} onLogout={logout}>{page}</SiteLayout>;

  return <Routes>
    <Route path="/" element={layout(<WelcomePage />)} />
    <Route path="/books" element={layout(<BookList />)} />
    <Route path="/book/:id" element={layout(<BookDetails onAddToCart={addToCart} />)} />
    <Route path="/login" element={layout(<AuthPage mode="login" onAuthenticated={setAuth} />)} />
    <Route path="/register" element={layout(<AuthPage mode="register" onAuthenticated={setAuth} />)} />
    <Route path="/cart" element={layout(<CartPage cart={cart} onChangeQuantity={changeQuantity} onRemove={removeFromCart} user={user} />)} />
    <Route path="/checkout" element={layout(<CheckoutPage cart={cart} user={user} token={auth?.token} clearCart={() => setCart([])} />)} />
    <Route path="*" element={layout(<div className="container py-5"><h1 className="h3">Seite nicht gefunden</h1><a href="/books">Zur Bücherübersicht</a></div>)} />
  </Routes>;
}
export default function App() {
  return <Router><AppRoutes /></Router>;
}
