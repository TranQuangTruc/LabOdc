import { apiJson } from "./api.js";
import { CONFIG } from "./config.js";

export function enterprisePay(projectId, amount) {
  return apiJson(`${CONFIG.PAYMENT_API_BASE}/api/payments/enterprise-pay`, {
    method: "POST",
    body: { projectId, amount }
  });
}

export function labAdvance(projectId, amount) {
  return apiJson(`${CONFIG.PAYMENT_API_BASE}/api/payments/lab-advance`, {
    method: "POST",
    body: { projectId, amount }
  });
}

export function getPayment(id) {
  return apiJson(`${CONFIG.PAYMENT_API_BASE}/api/payments/${encodeURIComponent(id)}`, {
    method: "GET"
  });
}
