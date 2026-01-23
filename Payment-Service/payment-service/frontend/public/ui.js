/*
  Very small UI to validate end-to-end connectivity:
  - Login against auth-service
  - Create/Get payments against payment-service

  IMPORTANT: UI talks to /auth/* and /api/* which are reverse-proxied by Nginx
  inside the frontend container to avoid CORS issues.
*/

const API = {
  login: "/auth/api/auth/login",
  paymentCreate: "/api/api/payments",
  paymentGet: (id) => `/api/api/payments/${encodeURIComponent(id)}`,
};

function $(id) {
  return document.getElementById(id);
}

function setText(id, text) {
  const el = $(id);
  if (el) el.textContent = text;
}

function setHTML(id, html) {
  const el = $(id);
  if (el) el.innerHTML = html;
}

function getToken() {
  return localStorage.getItem("jwt") || "";
}

function setToken(token) {
  localStorage.setItem("jwt", token || "");
}

async function http(url, { method = "GET", json, token, ...rest } = {}) {
  const headers = new Headers(rest.headers || {});
  if (json !== undefined) headers.set("Content-Type", "application/json");
  if (token) headers.set("Authorization", `Bearer ${token}`);

  const res = await fetch(url, {
    method,
    headers,
    body: json !== undefined ? JSON.stringify(json) : undefined,
    ...rest,
  });

  const text = await res.text();
  let data;
  try { data = text ? JSON.parse(text) : null; } catch { data = text; }
  if (!res.ok) {
    const msg = (data && (data.message || data.error)) ? (data.message || data.error) : `${res.status} ${res.statusText}`;
    throw new Error(msg);
  }
  return data;
}

async function refreshHealth() {
  setText("connStatus", "Checking...");
  const token = getToken();
  setText("tokenPreview", token ? `${token.slice(0, 24)}…` : "(none)");

  // We don't have explicit /health endpoints in these apps, so we use known routes:
  // - payment: GET /api/payments/{id} will 404 if not found but still indicates connectivity
  // - auth:   POST /api/auth/login with wrong creds returns 401/400; we don't want to spam.
  // We'll just mark the UI ready.
  setText("connStatus", "UI loaded. Use Login then Payment test.");
}

async function doLogin() {
  const username = $("username")?.value?.trim();
  const password = $("password")?.value?.trim();
  if (!username || !password) {
    setText("loginStatus", "Please enter username & password.");
    return;
  }

  setText("loginStatus", "Logging in...");
  try {
    const out = await http(API.login, { method: "POST", json: { username, password } });
    const token = out?.token || "";
    if (!token) throw new Error("No token returned");
    setToken(token);
    setText("loginStatus", "Login OK. Token saved.");
    await refreshHealth();
  } catch (e) {
    setToken("");
    setText("loginStatus", `Login failed: ${e.message}`);
  }
}

function logout() {
  setToken("");
  setText("loginStatus", "Logged out.");
  refreshHealth();
}

async function createPayment() {
  const token = getToken();
  const amount = Number($("amount")?.value);
  const method = $("method")?.value || "CARD";

  if (!Number.isFinite(amount) || amount <= 0) {
    setText("paymentStatus", "Amount must be a positive number.");
    return;
  }

  setText("paymentStatus", "Creating payment...");
  try {
    const payment = await http(API.paymentCreate, {
      method: "POST",
      json: { amount, method },
      token,
    });
    setText("paymentStatus", `Created payment id=${payment.id}`);
    setHTML(
      "paymentResult",
      `<pre>${escapeHtml(JSON.stringify(payment, null, 2))}</pre>`
    );
    if ($("paymentId")) $("paymentId").value = payment.id;
  } catch (e) {
    setText("paymentStatus", `Create failed: ${e.message}`);
  }
}

async function getPayment() {
  const token = getToken();
  const id = $("paymentId")?.value?.trim();
  if (!id) {
    setText("paymentStatus", "Enter a payment ID.");
    return;
  }

  setText("paymentStatus", "Fetching payment...");
  try {
    const payment = await http(API.paymentGet(id), { token });
    setText("paymentStatus", "Fetch OK.");
    setHTML(
      "paymentResult",
      `<pre>${escapeHtml(JSON.stringify(payment, null, 2))}</pre>`
    );
  } catch (e) {
    setText("paymentStatus", `Fetch failed: ${e.message}`);
  }
}

function escapeHtml(s) {
  return String(s)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;");
}

function init() {
  // Make sure scripts don't crash if markup changes.
  $("btnLogin")?.addEventListener("click", doLogin);
  $("btnLogout")?.addEventListener("click", logout);
  $("btnCreatePayment")?.addEventListener("click", createPayment);
  $("btnGetPayment")?.addEventListener("click", getPayment);

  // Default creds from InMemoryUserService
  if ($("username") && !$("username").value) $("username").value = "alice";
  if ($("password") && !$("password").value) $("password").value = "password1";

  refreshHealth();
}

// Ensure DOM is ready before querying elements.
document.addEventListener("DOMContentLoaded", init);
