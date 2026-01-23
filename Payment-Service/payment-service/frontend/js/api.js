import { CONFIG } from "./config.js";

export function getAuth() {
  try { return JSON.parse(localStorage.getItem(CONFIG.STORAGE_KEY) || "null"); }
  catch { return null; }
}

export function setAuth(auth) {
  localStorage.setItem(CONFIG.STORAGE_KEY, JSON.stringify(auth));
}

export function clearAuth() {
  localStorage.removeItem(CONFIG.STORAGE_KEY);
}

function headers(extra={}) {
  const h = { "Content-Type": "application/json", ...extra };
  const a = getAuth();
  if (a?.accessToken) h["Authorization"] = `Bearer ${a.accessToken}`;
  return h;
}

export async function apiFetch(base, path, {method="GET", body=null}={}) {
  const res = await fetch(`${base}${path}`, {
    method,
    headers: headers(),
    body: body ? JSON.stringify(body) : null
  });
  const text = await res.text();
  let data = null;
  try { data = text ? JSON.parse(text) : null; } catch { data = { raw: text }; }
  if (!res.ok) {
    const err = new Error(data?.message || `HTTP ${res.status}`);
    err.status = res.status;
    err.data = data;
    throw err;
  }
  return data;
}

// Convenience: call with a full URL
export async function apiJson(url, {method="GET", body=null}={}) {
  const res = await fetch(url, {
    method,
    headers: headers(),
    body: body ? JSON.stringify(body) : null
  });
  const text = await res.text();
  let data = null;
  try { data = text ? JSON.parse(text) : null; } catch { data = { raw: text }; }
  if (!res.ok) {
    const err = new Error(data?.message || `HTTP ${res.status}`);
    err.status = res.status;
    err.data = data;
    throw err;
  }
  return data;
}
