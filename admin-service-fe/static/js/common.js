/* LabOdc Admin FE (multi-page) - Vanilla HTML + JS */

const $ = (sel, root=document) => root.querySelector(sel);
const $$ = (sel, root=document) => Array.from(root.querySelectorAll(sel));

const state = { baseUrl: "", token: "" };

function escapeHtml(s){
  return String(s ?? "").replace(/[&<>"']/g, c => ({
    '&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'
  }[c]));
}
function fmt(dt){
  if(!dt) return "";
  return String(dt).replace("T"," ");
}

function effectiveBaseUrl(){
  return (state.baseUrl || window.location.origin).replace(/\/$/, "");
}

function setStatus(kind, text){
  const el = document.querySelector("#statusText");
  if(!el) return;
  el.innerHTML = `<span class="dot ${kind}"></span><span>${escapeHtml(text)}</span>`;
}

function toast(title, msg, kind="ok"){
  const wrap = document.querySelector("#toastWrap");
  if(!wrap) return;
  const el = document.createElement("div");
  el.className = `toast ${kind}`;
  el.innerHTML = `<div class="t">${escapeHtml(title)}</div><div class="m">${escapeHtml(msg)}</div>`;
  wrap.appendChild(el);
  setTimeout(() => { el.style.opacity = "0"; el.style.transform = "translateY(-6px)"; }, 4200);
  setTimeout(() => { el.remove(); }, 5200);
}

function loadCfg(){
  try{
    const cfg = JSON.parse(localStorage.getItem("labodc_admin_cfg")||"{}");
    state.baseUrl = cfg.baseUrl || "";
    state.token = cfg.token || "";
  }catch{}
  const bu = document.querySelector("#baseUrl");
  const tk = document.querySelector("#token");
  if(bu) bu.value = state.baseUrl;
  if(tk) tk.value = state.token;
}

function saveCfg(){
  const bu = document.querySelector("#baseUrl");
  const tk = document.querySelector("#token");
  state.baseUrl = (bu?.value || "").trim();
  state.token = (tk?.value || "").trim();
  localStorage.setItem("labodc_admin_cfg", JSON.stringify({baseUrl: state.baseUrl, token: state.token}));
  toast("Đã lưu cấu hình", `Base URL: ${effectiveBaseUrl()}\nToken: ${state.token ? "Có" : "Không"}`, "ok");
}

async function apiFetch(path, { method="GET", body=null, params=null, isForm=false } = {}){
  let url = effectiveBaseUrl() + path;
  if(params){
    const qs = new URLSearchParams(params);
    url += (url.includes("?") ? "&" : "?") + qs.toString();
  }

  const headers = {};
  if(state.token){
    headers["Authorization"] = state.token.startsWith("Bearer ") ? state.token : `Bearer ${state.token}`;
  }
  if(body && !isForm){
    headers["Content-Type"] = "application/json";
  }

  setStatus("warn", "Loading...");
  const res = await fetch(url, {
    method,
    headers,
    body: body ? (isForm ? body : JSON.stringify(body)) : null
  });

  const ct = res.headers.get("content-type") || "";
  if(!ct.includes("application/json")){
    if(!res.ok){
      const txt = await res.text().catch(()=> "");
      setStatus("bad", "Error");
      throw new Error(txt || `HTTP ${res.status}`);
    }
    setStatus("ok", "OK");
    return res;
  }

  const data = await res.json().catch(()=> ({}));
  if(!res.ok){
    setStatus("bad", "Error");
    const msg = data?.message || `HTTP ${res.status}`;
    const errs = data?.errors ? `\n${JSON.stringify(data.errors)}` : "";
    throw new Error(msg + errs);
  }
  if(data && data.success === false){
    setStatus("bad", "Error");
    throw new Error(data.message || "Request failed");
  }
  setStatus("ok", "OK");
  return data;
}

async function downloadWithAuth(path){
  // Supports Authorization header for file download endpoints
  const url = effectiveBaseUrl() + path;
  const headers = {};
  if(state.token){
    headers["Authorization"] = state.token.startsWith("Bearer ") ? state.token : `Bearer ${state.token}`;
  }
  setStatus("warn", "Downloading...");
  const res = await fetch(url, { headers });
  if(!res.ok){
    setStatus("bad", "Error");
    const txt = await res.text().catch(()=> "");
    throw new Error(txt || `HTTP ${res.status}`);
  }
  const blob = await res.blob();
  const cd = res.headers.get("content-disposition") || "";
  let filename = "download";
  const m = /filename=([^;]+)/i.exec(cd);
  if(m && m[1]) filename = m[1].trim().replace(/^"|"$/g, "");
  const a = document.createElement("a");
  a.href = URL.createObjectURL(blob);
  a.download = filename;
  document.body.appendChild(a);
  a.click();
  a.remove();
  setTimeout(() => URL.revokeObjectURL(a.href), 2000);
  setStatus("ok", "OK");
}

function badgeStatus(status){
  const s = String(status||"").toUpperCase();
  let kind = "";
  if(["APPROVED","ACTIVE","PUBLISHED","RUNNING"].includes(s)) kind="ok";
  else if(["PENDING","SUBMITTED"].includes(s)) kind="warn";
  else if(["SUSPENDED","REJECTED","CANCELLED","INACTIVE"].includes(s)) kind="bad";
  return `<span class="badge"><span class="dot ${kind}"></span><span>${escapeHtml(status||"")}</span></span>`;
}

function renderSidebar(active){
  const side = document.querySelector("#sidebar");
  if(!side) return;

  side.innerHTML = `
    <div class="brand">
      <div class="logo"></div>
      <div>
        <h1>LabOdc Admin</h1>
        <p>Multi-page (HTML + JS)</p>
      </div>
    </div>

    <nav class="nav" id="nav">
      <a href="/admin/enterprises" data-route="enterprises">Enterprises <small>Doanh nghiệp</small></a>
      <a href="/admin/projects" data-route="projects">Projects <small>Dự án</small></a>
      <a href="/admin/mentors" data-route="mentors">Mentors <small>Mentor</small></a>
      <a href="/admin/talents" data-route="talents">Talents <small>Talent</small></a>
      <a href="/admin/templates" data-route="templates">Excel Templates <small>Upload / tải</small></a>
      <a href="/admin/reports" data-route="reports">Reports <small>Tạo & publish</small></a>
      <a href="/admin/public-reports" data-route="public-reports">Public Reports <small>Endpoint /report</small></a>
    </nav>

    <div class="foot">
      <div class="muted" style="font-size:12px;line-height:1.35">
        API dùng trong admin-service:
        <div class="mono" style="margin-top:6px">/admin/enterprise</div>
        <div class="mono">/admin/project</div>
        <div class="mono">/admin/mentor</div>
        <div class="mono">/admin/talent</div>
        <div class="mono">/admin/template</div>
        <div class="mono">/admin/report</div>
        <div class="mono">/report</div>
      </div>

      <label>Base URL</label>
      <input id="baseUrl" placeholder="http://localhost:8080" />

      <label>JWT Token (tuỳ chọn)</label>
      <input id="token" placeholder="Bearer ..." />

      <div class="row">
        <button class="secondary" id="saveCfg">Lưu</button>
        <button class="secondary" id="ping">Ping</button>
      </div>

      <div class="hint">
        Nếu để trống Base URL và chạy cùng Spring Boot (static/), hệ thống tự dùng origin hiện tại.
      </div>
    </div>
  `;

  $$("#nav a").forEach(a => {
    a.classList.toggle("active", a.dataset.route === active);
  });

  loadCfg();

  const saveBtn = document.querySelector("#saveCfg");
  const pingBtn = document.querySelector("#ping");

  saveBtn?.addEventListener("click", saveCfg);
  pingBtn?.addEventListener("click", async ()=>{
    try{
      await apiFetch("/actuator/health");
      toast("Ping OK", "actuator/health trả về OK", "ok");
    }catch{
      try{
        await apiFetch("/report");
        toast("Ping OK", "GET /report trả về OK", "ok");
      }catch(e2){
        toast("Ping thất bại", e2.message, "bad");
      }
    }
  });

  const bu = document.querySelector("#baseUrl");
  const tk = document.querySelector("#token");
  bu?.addEventListener("change", saveCfg);
  tk?.addEventListener("change", saveCfg);
}

function setPageMeta(title, chips=[]){
  const t = document.querySelector("#pageTitle");
  const c = document.querySelector("#chips");
  if(t) t.textContent = title;
  if(c) c.innerHTML = (chips||[]).map(x => `<span class="chip">${escapeHtml(x)}</span>`).join("");
}

function initPage({activeNav, title, chips=[]}){
  renderSidebar(activeNav);
  setPageMeta(title, chips);
  setStatus("warn", "Idle");
}

function filterList(list, q){
  const s = (q||"").trim().toLowerCase();
  if(!s) return list || [];
  return (list||[]).filter(x => JSON.stringify(x||{}).toLowerCase().includes(s));
}

window.LabOdcAdmin = { $, $$, state, escapeHtml, fmt, toast, apiFetch, badgeStatus, initPage, filterList, downloadWithAuth, setStatus };
