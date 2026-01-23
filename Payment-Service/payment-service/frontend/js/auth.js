import { apiJson, setAuth, clearAuth } from "./api.js";
import { CONFIG } from "./config.js";

export async function login(username, password) {
  const data = await apiJson(`${CONFIG.AUTH_API_BASE}/api/auth/login`, {
    method: "POST",
    body: { username, password }
  });
  setAuth(data);
  return data;
}

export function logout() {
  clearAuth();
}
