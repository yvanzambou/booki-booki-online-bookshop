import axios from 'axios';

// Lokal: Backend auf :8080. In Docker wird VITE_API_URL=/booki-booki gesetzt (nginx leitet an das Backend weiter).
const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/booki-booki";

export async function getAllBooks() {
    const res = await axios.get(`${API_URL}/books`);
    return res.data;
}

export async function getAllGenres() {
    const res = await axios.get(`${API_URL}/genres`);
    return res.data;
}

export async function getBooksByGenre(genre) {
    const res = await axios.get(`${API_URL}/books/${genre}`);
    return res.data;
}

export async function getBookById(id) {
  const res = await axios.get(`${API_URL}/book/${id}`);
  return res.data;
}


export function formatEuroDE(amount) {
    // Preis kommt als String aus dem Backend; ungueltige Werte nicht als "NaN €" anzeigen
    const value = Number(String(amount ?? "").replace(",", "."));
    if (!Number.isFinite(value)) return "Preis auf Anfrage";
    amount = value;
    const formatted = new Intl.NumberFormat('de-DE', {
        style: 'currency',
        currency: 'EUR',
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(amount);
    return formatted;
}

const AUTH_URL = `${API_URL}/auth`;
export async function registerCustomer(payload) {
  const res = await axios.post(`${AUTH_URL}/register`, payload);
  return res.data;
}
export async function loginCustomer(payload) {
  const res = await axios.post(`${AUTH_URL}/login`, payload);
  return res.data;
}
export async function logoutCustomer(token) {
  await axios.post(`${AUTH_URL}/logout`, {}, { headers: { Authorization: `Bearer ${token}` } });
}
export async function savePaymentProfile(token, payload) {
  return axios.put(`${API_URL}/account/payment-profile`, payload, {
    headers: { Authorization: `Bearer ${token}` }
  });
}
export async function getPaymentProfile(token) {
  const res = await axios.get(`${API_URL}/account/payment-profile`, {
    headers: { Authorization: `Bearer ${token}` }
  });
  return res.status === 204 ? null : res.data;
}

export async function getSavedCart(token) {
  const res = await axios.get(`${API_URL}/account/cart`, {
    headers: { Authorization: `Bearer ${token}` }
  });
  return (res.data || []).map(line => ({ ...line.book, quantity: line.quantity }));
}

export async function saveCart(token, cart) {
  const payload = (cart || []).map(item => ({ bookId: item.id, quantity: item.quantity }));
  const res = await axios.put(`${API_URL}/account/cart`, payload, {
    headers: { Authorization: `Bearer ${token}` }
  });
  return (res.data || []).map(line => ({ ...line.book, quantity: line.quantity }));
}
