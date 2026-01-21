
(() => {
  const { $, toast, apiFetch, badgeStatus, initPage, filterList } = window.LabOdcAdmin;

  initPage({
    activeNav: "talents",
    title: "Talents",
    chips: ["GET /admin/talent", "GET /admin/talent/{id}", "POST /{id}/activate", "POST /{id}/suspend"]
  });

  const view = $("#view");
  view.innerHTML = `
    <div class="grid">
      <div class="card">
        <div class="hd"><div class="title">Danh sách talent</div><div class="muted">Activate / Suspend</div></div>
        <div class="bd">
          <div class="toolbar">
            <div class="left">
              <input class="search" id="q" placeholder="Search name/email/status..." />
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
        <td>${(x.fullName ?? "")}</td>
        <td>${(x.email ?? "")}</td>
        <td>${badgeStatus(x.status)}</td>
        <td>
          <div class="actions">
            <button class="ok" data-act="activate" data-id="${x.id}">Activate</button>
            <button class="danger" data-act="suspend" data-id="${x.id}">Suspend</button>
            <button class="secondary" data-act="detail" data-id="${x.id}">Detail</button>
          </div>
        </td>
      </tr>
    `).join("");

    tbl.innerHTML = `
      <table>
        <thead>
          <tr><th>ID</th><th>Full name</th><th>Email</th><th>Status</th><th>Actions</th></tr>
        </thead>
        <tbody>
          ${rows || `<tr><td colspan="5" class="muted">No data</td></tr>`}
        </tbody>
      </table>
    `;
  }

  async function load(force=false){
    try{
      const res = await apiFetch("/admin/talent");
      items = res.data || [];
      render(filterList(items, q.value));
      if(force) toast("Đã refresh", `Talents: ${items.length}`, "ok");
    }catch(e){
      toast("Lỗi tải talents", e.message, "bad");
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
      if(act === "activate"){
        await apiFetch(`/admin/talent/${id}/activate`, {method:"POST"});
        toast("Activate thành công", id, "ok");
        await load();
      } else if(act === "suspend"){
        await apiFetch(`/admin/talent/${id}/suspend`, {method:"POST"});
        toast("Suspend thành công", id, "warn");
        await load();
      } else if(act === "detail"){
        const res = await apiFetch(`/admin/talent/${id}`);
        const d = res.data || {};
        toast("Talent detail", `Full name: ${d.fullName || ""}\nEmail: ${d.email || ""}\nStatus: ${d.status || ""}`, "ok");
      }
    }catch(e){
      toast("Thao tác thất bại", e.message, "bad");
    }
  });

  load();
})();
