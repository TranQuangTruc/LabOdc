
(() => {
  const { $, toast, apiFetch, badgeStatus, initPage, filterList } = window.LabOdcAdmin;

  initPage({
    activeNav: "projects",
    title: "Projects",
    chips: ["GET /admin/project", "GET /admin/project/{id}", "POST /{id}/approve", "POST /{id}/reject?reason=...", "POST /{id}/cancel"]
  });

  const view = $("#view");
  view.innerHTML = `
    <div class="grid">
      <div class="card">
        <div class="hd"><div class="title">Danh sách dự án</div><div class="muted">Duyệt / từ chối / huỷ</div></div>
        <div class="bd">
          <div class="toolbar">
            <div class="left">
              <input class="search" id="q" placeholder="Search title/enterprise/status..." />
              <button class="secondary" id="btnRefresh">Refresh</button>
            </div>
            <div class="right"></div>
          </div>
          <div id="tbl"></div>
        </div>
      </div>
    </div>
  `;

  const q = $("#q");
  const tbl = $("#tbl");

  let items = [];

  function render(list){
    const rows = (list||[]).map(x => `
      <tr>
        <td class="mono">${x.id ?? ""}</td>
        <td>${(x.title ?? "")}</td>
        <td>${(x.enterpriseName ?? "")}</td>
        <td>${badgeStatus(x.status)}</td>
        <td>
          <div class="actions">
            <button class="ok" data-act="approve" data-id="${x.id}">Approve</button>
            <button class="warn" data-act="reject" data-id="${x.id}">Reject</button>
            <button class="danger" data-act="cancel" data-id="${x.id}">Cancel</button>
            <button class="secondary" data-act="detail" data-id="${x.id}">Detail</button>
          </div>
        </td>
      </tr>
    `).join("");

    tbl.innerHTML = `
      <table>
        <thead>
          <tr><th>ID</th><th>Title</th><th>Enterprise</th><th>Status</th><th>Actions</th></tr>
        </thead>
        <tbody>
          ${rows || `<tr><td colspan="5" class="muted">No data</td></tr>`}
        </tbody>
      </table>
    `;
  }

  async function load(force=false){
    try{
      const res = await apiFetch("/admin/project");
      items = res.data || [];
      render(filterList(items, q.value));
      if(force) toast("Đã refresh", `Projects: ${items.length}`, "ok");
    }catch(e){
      toast("Lỗi tải projects", e.message, "bad");
    }
  }

  $("#btnRefresh").addEventListener("click", () => load(true));
  q.addEventListener("input", () => render(filterList(items, q.value)));

  tbl.addEventListener("click", async (ev) => {
    const btn = ev.target.closest("button");
    if(!btn) return;
    const id = btn.dataset.id;
    const act = btn.dataset.act;
    try{
      if(act === "approve"){
        await apiFetch(`/admin/project/${id}/approve`, {method:"POST"});
        toast("Approve thành công", id, "ok");
        await load();
      } else if(act === "reject"){
        const reason = prompt("Nhập lý do reject (reason):", "Thiếu thông tin / không phù hợp");
        if(reason == null) return;
        await apiFetch(`/admin/project/${id}/reject`, {method:"POST", params:{reason}});
        toast("Reject thành công", `ID: ${id}\nReason: ${reason}`, "warn");
        await load();
      } else if(act === "cancel"){
        await apiFetch(`/admin/project/${id}/cancel`, {method:"POST"});
        toast("Cancel thành công", id, "warn");
        await load();
      } else if(act === "detail"){
        const res = await apiFetch(`/admin/project/${id}`);
        const d = res.data || {};
        toast("Project detail", `Title: ${d.title || ""}\nEnterprise: ${d.enterpriseName || ""}\nStatus: ${d.status || ""}`, "ok");
      }
    }catch(e){
      toast("Thao tác thất bại", e.message, "bad");
    }
  });

  load();
})();
