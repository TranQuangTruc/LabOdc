
(() => {
  const { $, toast, apiFetch, initPage } = window.LabOdcAdmin;

  initPage({
    activeNav: "",
    title: "Admin Home",
    chips: ["Quick links", "Ping", "Counts (optional)"]
  });

  const view = $("#view");
  view.innerHTML = `
    <div class="grid">
      <div class="card">
        <div class="hd"><div class="title">LabOdc Admin Dashboard</div><div class="muted">Multi-page</div></div>
        <div class="bd">
          <div class="actions" style="margin-bottom:12px">
            <a href="enterprises.html"><button class="secondary">Enterprises</button></a>
            <a href="projects.html"><button class="secondary">Projects</button></a>
            <a href="mentors.html"><button class="secondary">Mentors</button></a>
            <a href="talents.html"><button class="secondary">Talents</button></a>
            <a href="templates.html"><button class="secondary">Templates</button></a>
            <a href="reports.html"><button class="secondary">Reports</button></a>
            <a href="public-reports.html"><button class="secondary">Public Reports</button></a>
          </div>

          <div class="hint">
            Gợi ý chạy: copy toàn bộ thư mục FE này vào <span class="mono">src/main/resources/static</span> của admin-service,
            sau đó mở <span class="mono">/index.html</span>.
          </div>

          <div style="margin-top:14px; display:flex; gap:10px; flex-wrap:wrap">
            <button class="ok" id="btnLoadCounts">Load counts</button>
            <button class="secondary" id="btnHealth">Health</button>
          </div>

          <div id="counts" style="margin-top:14px"></div>
        </div>
      </div>
    </div>
  `;

  async function loadCounts(){
    const c = $("#counts");
    c.innerHTML = `<div class="muted">Loading...</div>`;
    try{
      const [e,p,m,t,r] = await Promise.all([
        apiFetch("/admin/enterprise"),
        apiFetch("/admin/project"),
        apiFetch("/admin/mentor"),
        apiFetch("/admin/talent"),
        apiFetch("/admin/report"),
      ]);
      const counts = {
        enterprises: (e.data||[]).length,
        projects: (p.data||[]).length,
        mentors: (m.data||[]).length,
        talents: (t.data||[]).length,
        reports: (r.data||[]).length,
      };
      c.innerHTML = `
        <table>
          <thead><tr><th>Resource</th><th>Count</th></tr></thead>
          <tbody>
            <tr><td>Enterprises</td><td class="mono">${counts.enterprises}</td></tr>
            <tr><td>Projects</td><td class="mono">${counts.projects}</td></tr>
            <tr><td>Mentors</td><td class="mono">${counts.mentors}</td></tr>
            <tr><td>Talents</td><td class="mono">${counts.talents}</td></tr>
            <tr><td>Reports</td><td class="mono">${counts.reports}</td></tr>
          </tbody>
        </table>
      `;
      toast("Counts OK", JSON.stringify(counts, null, 0), "ok");
    }catch(e){
      c.innerHTML = `<div class="muted">Không load được counts: ${e.message}</div>`;
      toast("Counts lỗi", e.message, "bad");
    }
  }

  async function health(){
    try{
      await apiFetch("/actuator/health");
      toast("Health OK", "actuator/health", "ok");
    }catch(e){
      toast("Health lỗi", e.message, "bad");
    }
  }

  $("#btnLoadCounts").addEventListener("click", loadCounts);
  $("#btnHealth").addEventListener("click", health);
})();
