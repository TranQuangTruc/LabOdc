
(() => {
  const { $, toast, apiFetch, initPage } = window.LabOdcAdmin;

  initPage({
    activeNav: "reports",
    title: "Reports",
    chips: ["POST /admin/report", "GET /admin/report", "PATCH /admin/report/{id}/publish"]
  });

  const view = $("#view");
  view.innerHTML = `
    <div class="grid">
      <div class="card">
        <div class="hd"><div class="title">Tạo report</div><div class="muted">MONTHLY / PHASE / FINAL</div></div>
        <div class="bd">
          <form id="createForm">
            <div class="split">
              <div class="field"><label>Title</label><input name="title" placeholder="Báo cáo tháng 2026-01" required></div>
              <div class="field"><label>Type</label>
                <select name="type" required>
                  <option value="MONTHLY">MONTHLY</option>
                  <option value="PHASE">PHASE</option>
                  <option value="FINAL">FINAL</option>
                </select>
              </div>
            </div>
            <div class="field" style="margin-top:10px"><label>Period (YYYY-MM)</label><input name="period" placeholder="2026-01" required></div>
            <div class="field" style="margin-top:10px"><label>Content (optional)</label><textarea name="content" placeholder="Nội dung..."></textarea></div>
            <div style="margin-top:12px; display:flex; gap:8px; flex-wrap:wrap">
              <button class="ok" type="submit">Create</button>
              <button class="secondary" type="button" id="btnRefresh">Refresh</button>
            </div>
            <div class="hint">Body JSON: <span class="mono">{title, content, type, period}</span></div>
          </form>
        </div>
      </div>

      <div class="card">
        <div class="hd"><div class="title">Danh sách report</div><div class="muted">Publish để public</div></div>
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
        <td>${x.title ?? ""}</td>
        <td class="muted">${(x.publishedAt ?? "").replace("T"," ")}</td>
        <td>
          <div class="actions">
            <button class="ok" data-act="publish" data-id="${x.id}">Publish</button>
          </div>
        </td>
      </tr>
    `).join("");

    tbl.innerHTML = `
      <table>
        <thead><tr><th>ID</th><th>Title</th><th>Published At</th><th>Actions</th></tr></thead>
        <tbody>
          ${rows || `<tr><td colspan="4" class="muted">No data</td></tr>`}
        </tbody>
      </table>
    `;
  }

  async function load(force=false){
    try{
      const res = await apiFetch("/admin/report");
      items = res.data || [];
      render();
      if(force) toast("Đã refresh", `Reports: ${items.length}`, "ok");
    }catch(e){
      toast("Lỗi tải reports", e.message, "bad");
    }
  }

  $("#btnRefresh").addEventListener("click", () => load(true));

  $("#createForm").addEventListener("submit", async (ev) => {
    ev.preventDefault();
    const fd = new FormData(ev.target);
    const body = {
      title: fd.get("title"),
      content: (fd.get("content") || "").trim() || null,
      type: fd.get("type"),
      period: fd.get("period"),
    };
    try{
      await apiFetch("/admin/report", {method:"POST", body});
      toast("Create report thành công", body.title, "ok");
      ev.target.reset();
      await load();
    }catch(e){
      toast("Create report thất bại", e.message, "bad");
    }
  });

  tbl.addEventListener("click", async (ev) => {
    const btn = ev.target.closest("button");
    if(!btn) return;
    if(btn.dataset.act === "publish"){
      const id = btn.dataset.id;
      try{
        await apiFetch(`/admin/report/${id}/publish`, {method:"PATCH"});
        toast("Publish thành công", id, "ok");
        await load();
      }catch(e){
        toast("Publish thất bại", e.message, "bad");
      }
    }
  });

  load();
})();
