
(() => {
  const { $, toast, apiFetch, initPage, downloadWithAuth } = window.LabOdcAdmin;

  initPage({
    activeNav: "templates",
    title: "Excel Templates",
    chips: ["POST /admin/template/upload", "GET /admin/template", "PATCH /admin/template/{id}/deactivate", "GET /admin/template/active/{name}"]
  });

  const view = $("#view");
  view.innerHTML = `
    <div class="grid">
      <div class="card">
        <div class="hd"><div class="title">Upload template</div><div class="muted">.xlsx</div></div>
        <div class="bd">
          <form id="uploadForm">
            <div class="split">
              <div class="field"><label>Name</label><input name="name" placeholder="task_breakdown" required></div>
              <div class="field"><label>Version</label><input name="version" placeholder="v1.0" required></div>
            </div>
            <div class="field" style="margin-top:10px"><label>Description (optional)</label><input name="description" placeholder="Mô tả ngắn..."></div>
            <div class="field" style="margin-top:10px"><label>File</label><input name="file" type="file" accept=".xlsx,.xls" required></div>
            <div style="margin-top:12px; display:flex; gap:8px; flex-wrap:wrap">
              <button class="ok" type="submit">Upload</button>
              <button class="secondary" type="button" id="btnRefresh">Refresh</button>
            </div>
            <div class="hint">Endpoint upload là multipart: <span class="mono">name, version, description, file</span></div>
          </form>
        </div>
      </div>

      <div class="card">
        <div class="hd"><div class="title">Download active template</div><div class="muted">theo name</div></div>
        <div class="bd">
          <div class="split">
            <div class="field"><label>Template name</label><input id="dlName" placeholder="task_breakdown"></div>
            <div class="field"><label>&nbsp;</label><button class="secondary" id="btnDownload" type="button">Download</button></div>
          </div>
          <div class="hint">Download có hỗ trợ Authorization header.</div>
        </div>
      </div>

      <div class="card">
        <div class="hd"><div class="title">Danh sách template</div><div class="muted">Deactivate khi cần</div></div>
        <div class="bd">
          <div id="tbl"></div>
        </div>
      </div>
    </div>
  `;

  const tbl = $("#tbl");
  let items = [];

  function render(){
    const rows = (items||[]).map(x => `
      <tr>
        <td class="mono">${x.id ?? ""}</td>
        <td>${x.name ?? ""}</td>
        <td class="muted">${(x.uploadedAt ?? "").replace("T"," ")}</td>
        <td class="mono">${x.fileUrl ?? ""}</td>
        <td>
          <div class="actions">
            <button class="danger" data-act="deactivate" data-id="${x.id}">Deactivate</button>
          </div>
        </td>
      </tr>
    `).join("");

    tbl.innerHTML = `
      <table>
        <thead><tr><th>ID</th><th>Name</th><th>Uploaded</th><th>File URL</th><th>Actions</th></tr></thead>
        <tbody>
          ${rows || `<tr><td colspan="5" class="muted">No data</td></tr>`}
        </tbody>
      </table>
    `;
  }

  async function load(force=false){
    try{
      const res = await apiFetch("/admin/template");
      items = res.data || [];
      render();
      if(force) toast("Đã refresh", `Templates: ${items.length}`, "ok");
    }catch(e){
      toast("Lỗi tải templates", e.message, "bad");
    }
  }

  $("#btnRefresh").addEventListener("click", () => load(true));

  $("#uploadForm").addEventListener("submit", async (ev) => {
    ev.preventDefault();
    const fd = new FormData(ev.target);
    try{
      await apiFetch("/admin/template/upload", { method:"POST", body: fd, isForm:true });
      toast("Upload thành công", `Name: ${fd.get("name")}\nVersion: ${fd.get("version")}`, "ok");
      ev.target.reset();
      await load();
    }catch(e){
      toast("Upload thất bại", e.message, "bad");
    }
  });

  $("#btnDownload").addEventListener("click", async () => {
    const name = $("#dlName").value.trim();
    if(!name){
      toast("Thiếu name", "Nhập template name để tải bản active.", "warn");
      return;
    }
    try{
      await downloadWithAuth(`/admin/template/active/${encodeURIComponent(name)}`);
      toast("Download bắt đầu", name, "ok");
    }catch(e){
      toast("Download thất bại", e.message, "bad");
    }
  });

  tbl.addEventListener("click", async (ev) => {
    const btn = ev.target.closest("button");
    if(!btn) return;
    if(btn.dataset.act === "deactivate"){
      const id = btn.dataset.id;
      try{
        await apiFetch(`/admin/template/${id}/deactivate`, {method:"PATCH"});
        toast("Deactivate thành công", id, "warn");
        await load();
      }catch(e){
        toast("Thao tác thất bại", e.message, "bad");
      }
    }
  });

  load();
})();
