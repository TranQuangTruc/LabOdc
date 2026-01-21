
(() => {
  const { $, toast, apiFetch, initPage } = window.LabOdcAdmin;

  initPage({
    activeNav: "public-reports",
    title: "Public Reports",
    chips: ["GET /report"]
  });

  const view = $("#view");
  view.innerHTML = `
    <div class="grid">
      <div class="card">
        <div class="hd"><div class="title">Danh sách report đã publish</div><div class="muted">Public endpoint</div></div>
        <div class="bd">
          <div class="toolbar">
            <div class="left"><div class="muted">Nguồn: <span class="mono">GET /report</span></div></div>
            <div class="right"><button class="secondary" id="btnRefresh">Refresh</button></div>
          </div>
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
      </tr>
    `).join("");

    tbl.innerHTML = `
      <table>
        <thead><tr><th>ID</th><th>Title</th><th>Published At</th></tr></thead>
        <tbody>
          ${rows || `<tr><td colspan="3" class="muted">No data</td></tr>`}
        </tbody>
      </table>
    `;
  }

  async function load(force=false){
    try{
      const res = await apiFetch("/report");
      items = res.data || [];
      render();
      if(force) toast("Đã refresh", `Public reports: ${items.length}`, "ok");
    }catch(e){
      toast("Lỗi tải public reports", e.message, "bad");
    }
  }

  $("#btnRefresh").addEventListener("click", () => load(true));
  load();
})();
