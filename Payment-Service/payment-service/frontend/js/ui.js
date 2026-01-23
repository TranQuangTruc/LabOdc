import { CONFIG } from "./config.js";
import { getAuth } from "./api.js";
import { login, logout } from "./auth.js";
import { enterprisePay, labAdvance, getPayment } from "./payments.js";

const $ = (q) => document.querySelector(q);

function pretty(x) { return x ? JSON.stringify(x, null, 2) : ""; }

function toast(msg, type="ok") {
  const t = $("#toast");
  t.classList.remove("hidden","ok","err");
  t.classList.add(type === "err" ? "err" : "ok");
  t.textContent = msg;
  setTimeout(() => t.classList.add("hidden"), 3200);
}

function refreshAuth() {
  const a = getAuth();
  $("#who").textContent = a?.username ? `${a.username} (${a.role})` : "Chưa đăng nhập";
}

async function onLogin() {
  try {
    const u = $("#username").value.trim();
    const p = $("#password").value.trim();
    const r = await login(u, p);
    toast("Login OK");
    $("#loginOut").textContent = pretty(r);
    refreshAuth();
  } catch (e) {
    toast(e.message || "Login fail", "err");
  }
}

function onLogout() {
  logout();
  refreshAuth();
  toast("Logged out");
}

async function onEnterprisePay() {
  try {
    const projectId = $("#projectId").value.trim() || "PRJ-001";
    const amount = Number($("#amount").value || 0);
    const r = await enterprisePay(projectId, amount);
    $("#payOut").textContent = pretty(r);
    if (r?.paymentId) $("#paymentId").value = r.paymentId;
    toast("Enterprise pay OK");
  } catch (e) {
    toast(e.message || "Pay fail", "err");
  }
}

async function onLabAdvance() {
  try {
    const projectId = $("#projectId").value.trim() || "PRJ-001";
    const amount = Number($("#amount").value || 0);
    const r = await labAdvance(projectId, amount);
    $("#payOut").textContent = pretty(r);
    if (r?.paymentId) $("#paymentId").value = r.paymentId;
    toast("Lab advance OK");
  } catch (e) {
    toast(e.message || "Advance fail", "err");
  }
}

async function onGetPayment() {
  try {
    const id = $("#paymentId").value.trim();
    const r = await getPayment(id);
    $("#getOut").textContent = pretty(r);
    toast("Loaded");
  } catch (e) {
    toast(e.message || "Get fail", "err");
  }
}

function init() {
  $("#bases").textContent = `AUTH: ${CONFIG.AUTH_API_BASE} | PAYMENT: ${CONFIG.PAYMENT_API_BASE}`;
  refreshAuth();
  $("#btnLogin").addEventListener("click", onLogin);
  $("#btnDemo").addEventListener("click", () => { $("#username").value="enterprise"; $("#password").value="enterprise"; });
  $("#btnLogout").addEventListener("click", onLogout);
  $("#btnEnterprisePay").addEventListener("click", onEnterprisePay);
  $("#btnLabAdvance").addEventListener("click", onLabAdvance);
  $("#getPayment").addEventListener("click", onGetPayment);
}

init();
