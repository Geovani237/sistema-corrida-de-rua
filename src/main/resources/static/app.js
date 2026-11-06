(() => {
  "use strict";

  const defaultBase = window.location.origin;
  let apiBase = localStorage.getItem("pace-api-base") || defaultBase;
  let races = [];
  let runners = [];

  const $ = (selector, root = document) => root.querySelector(selector);
  const $$ = (selector, root = document) => [...root.querySelectorAll(selector)];
  const apiRoot = () => `${apiBase.replace(/\/+$/, "")}/api/v1`;

  function safeText(value) {
    return String(value ?? "");
  }

  function escapeHtml(value) {
    return safeText(value).replace(/[&<>"']/g, char => ({
      "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;"
    })[char]);
  }

  function formatDate(value, options = { day: "2-digit", month: "short", year: "numeric" }) {
    if (!value) return "Data não informada";
    const date = new Date(value);
    if (Number.isNaN(date.getTime())) return safeText(value);
    return new Intl.DateTimeFormat("pt-BR", options).format(date);
  }

  function toast(message, isError = false) {
    const region = $("#toast-region");
    const item = document.createElement("div");
    item.className = `toast${isError ? " error" : ""}`;
    item.textContent = message;
    region.append(item);
    window.setTimeout(() => item.remove(), 4800);
  }

  async function request(path, options = {}) {
    const response = await fetch(`${apiRoot()}${path}`, {
      ...options,
      headers: { ...(options.body ? { "Content-Type": "application/json" } : {}), ...options.headers }
    });
    const raw = await response.text();
    let payload = null;
    if (raw) {
      try {
        payload = JSON.parse(raw);
      } catch {
        payload = raw;
      }
    }
    if (!response.ok) {
      const detail = payload && typeof payload === "object"
        ? payload.message || payload.error
        : payload;
      throw new Error(detail || `A API respondeu com erro ${response.status}.`);
    }
    return payload;
  }

  function setApiStatus(state, message) {
    const dot = $("#status-dot");
    dot.className = `status-dot ${state}`;
    $("#status-label").textContent = message;
    $("#status-url").textContent = apiRoot().replace(/^https?:\/\//, "");
  }

  function currentPage() {
    const id = (window.location.hash || "#inicio").slice(1);
    return ["inicio", "corridas", "corredores", "operacoes", "resultados"].includes(id) ? id : "inicio";
  }

  function showPage() {
    const page = currentPage();
    $$(".page").forEach(element => element.classList.toggle("active", element.id === `page-${page}`));
    $$(".nav-link").forEach(link => link.classList.toggle("active", link.dataset.page === page));
    const activeLink = $(`.nav-link[data-page="${page}"]`);
    $("#breadcrumb-current").textContent = activeLink?.lastElementChild?.textContent || "Visão geral";
    if (page === "corridas") loadRaces();
    if (page === "corredores") loadRunners();
  }

  function renderRaceSelects() {
    const selects = $$(".race-select, #runner-race-select");
    selects.forEach(select => {
      const previous = select.value;
      const options = races.map(race =>
        `<option value="${escapeHtml(race.id)}">${escapeHtml(race.local)} · ${escapeHtml(formatDate(race.data))} · ${escapeHtml(race.distancia)} km</option>`
      ).join("");
      select.innerHTML = `<option value="">${races.length ? "Selecione uma corrida" : "Cadastre uma corrida primeiro"}</option>${options}`;
      if (races.some(race => String(race.id) === previous)) select.value = previous;
      select.disabled = races.length === 0;
    });
  }

  function raceDateBadge(value) {
    const date = new Date(value);
    if (Number.isNaN(date.getTime())) return '<span class="race-date-icon">—</span>';
    return `<span class="race-date-icon">${escapeHtml(new Intl.DateTimeFormat("pt-BR", { month: "short" }).format(date).replace(".", ""))}<strong>${date.getDate()}</strong></span>`;
  }

  function renderDashboardRaces() {
    const container = $("#dashboard-races");
    const now = new Date();
    const upcoming = [...races]
      .sort((a, b) => new Date(a.data) - new Date(b.data))
      .filter(race => !Number.isNaN(new Date(race.data).getTime()) && new Date(race.data) >= now)
      .slice(0, 4);
    const items = upcoming.length ? upcoming : [...races].slice(0, 4);
    $("#stat-next-race").textContent = items.length ? formatDate(items[0].data, { day: "2-digit", month: "short" }) : "—";
    if (!items.length) {
      container.innerHTML = '<div class="empty-state"><strong>Nenhuma corrida por aqui</strong><p>Cadastre seu primeiro evento para começar.</p></div>';
      return;
    }
    container.innerHTML = items.map(race => `
      <article class="race-row">
        ${raceDateBadge(race.data)}
        <div><div class="race-row-name">${escapeHtml(race.local)}</div><div class="race-row-meta">${escapeHtml(formatDate(race.data, { weekday: "long", hour: "2-digit", minute: "2-digit" }))} · Corrida #${escapeHtml(race.id)}</div></div>
        <span class="race-distance">${escapeHtml(race.distancia)} km</span>
      </article>`).join("");
  }

  function renderRaceTable() {
    const body = $("#races-table-body");
    if (!races.length) {
      body.innerHTML = '<tr><td colspan="4" class="empty-cell">Nenhuma corrida cadastrada ainda.</td></tr>';
      return;
    }
    body.innerHTML = [...races].sort((a, b) => new Date(a.data) - new Date(b.data)).map(race => `
      <tr>
        <td><strong>${escapeHtml(race.local)}</strong></td>
        <td>${escapeHtml(formatDate(race.data))}</td>
        <td>${escapeHtml(race.distancia)} km</td>
        <td>#${escapeHtml(race.id)}</td>
      </tr>`).join("");
  }

  function renderRunnerTable() {
    const body = $("#runners-table-body");
    if (!runners.length) {
      body.innerHTML = '<tr><td colspan="4" class="empty-cell">Nenhum corredor cadastrado ainda.</td></tr>';
      return;
    }
    body.innerHTML = runners.map(runner => {
      const count = Array.isArray(runner.corridaIds) ? runner.corridaIds.length : 0;
      const category = safeText(runner.categoria).toUpperCase();
      return `<tr>
        <td><strong>${escapeHtml(runner.nome)}</strong><div class="table-muted">${escapeHtml(runner.idade)} anos</div></td>
        <td><span class="category-pill${category === "ELITE" ? " elite" : ""}">${escapeHtml(category)}</span></td>
        <td>${count}</td>
        <td>#${escapeHtml(runner.numeroPeito)}</td>
      </tr>`;
    }).join("");
  }

  async function loadRaces() {
    try {
      const result = await request("/corridas");
      races = Array.isArray(result) ? result : [];
      $("#stat-races").textContent = races.length;
      renderRaceTable();
      renderDashboardRaces();
      renderRaceSelects();
      setApiStatus("online", "API conectada");
    } catch (error) {
      $("#races-table-body").innerHTML = '<tr><td colspan="4" class="empty-cell">Não foi possível carregar as corridas.</td></tr>';
      $("#dashboard-races").innerHTML = '<div class="loading-state">Falha ao carregar dados da API.</div>';
      setApiStatus("offline", "API indisponível");
      toast(`Não foi possível carregar corridas: ${error.message}`, true);
    }
  }

  async function loadRunners() {
    try {
      const result = await request("/corredores");
      runners = Array.isArray(result) ? result : [];
      $("#stat-runners").textContent = runners.length;
      renderRunnerTable();
      setApiStatus("online", "API conectada");
    } catch (error) {
      $("#runners-table-body").innerHTML = '<tr><td colspan="4" class="empty-cell">Não foi possível carregar os corredores.</td></tr>';
      setApiStatus("offline", "API indisponível");
      toast(`Não foi possível carregar corredores: ${error.message}`, true);
    }
  }

  function setBusy(form, busy) {
    const button = $('button[type="submit"]', form);
    if (button) {
      button.disabled = busy;
      button.dataset.originalText ||= button.textContent;
      button.textContent = busy ? "Processando…" : button.dataset.originalText;
    }
  }

  async function submitForm(form, operation, successMessage, refresh = []) {
    const data = new FormData(form);
    setBusy(form, true);
    try {
      await operation(data);
      toast(successMessage);
      form.reset();
      await Promise.all(refresh.map(fn => fn()));
    } catch (error) {
      toast(error.message, true);
    } finally {
      setBusy(form, false);
    }
  }

  function wireForms() {
    $("#race-form").addEventListener("submit", event => {
      event.preventDefault();
      const form = event.currentTarget;
      submitForm(form, data => request("/corridas", {
        method: "POST",
        body: JSON.stringify({
          local: data.get("local").trim(),
          distancia: Number(data.get("distancia")),
          data: `${data.get("data")}:00`
        })
      }), "Corrida cadastrada com sucesso.", [loadRaces]);
    });

    $("#runner-form").addEventListener("submit", event => {
      event.preventDefault();
      const form = event.currentTarget;
      submitForm(form, data => request("/corredores", {
        method: "POST",
        body: JSON.stringify({
          nome: data.get("nome").trim(),
          idade: Number(data.get("idade")),
          categoria: data.get("categoria"),
          corridaId: Number(data.get("corridaId"))
        })
      }), "Corredor cadastrado com sucesso.", [loadRunners, loadRaces]);
    });

    $("#kit-form").addEventListener("submit", event => {
      event.preventDefault();
      const form = event.currentTarget;
      submitForm(form, data => request(`/corredores/${encodeURIComponent(data.get("corredorId"))}/kits`, { method: "PUT" }), "Retirada do kit confirmada.");
    });

    $("#finish-form").addEventListener("submit", event => {
      event.preventDefault();
      const form = event.currentTarget;
      submitForm(form, data => request(`/corredores/${encodeURIComponent(data.get("corredorId"))}/corridas/${encodeURIComponent(data.get("corridaId"))}/chegada`, { method: "PUT" }), "Chegada registrada com sucesso.");
    });

    $("#link-form").addEventListener("submit", event => {
      event.preventDefault();
      const form = event.currentTarget;
      submitForm(form, data => request(`/corredores/${encodeURIComponent(data.get("numeroPeito"))}/corridas/${encodeURIComponent(data.get("corridaId"))}`, { method: "PUT" }), "Corrida vinculada ao corredor com sucesso.", [loadRunners]);
    });

    $("#results-form").addEventListener("submit", async event => {
      event.preventDefault();
      const form = event.currentTarget;
      const corridaId = new FormData(form).get("corridaId");
      const content = $("#results-content");
      content.innerHTML = '<div class="loading-state">Buscando resultados…</div>';
      try {
        const result = await request(`/corridas/${encodeURIComponent(corridaId)}/resultado`);
        content.innerHTML = renderResults(result);
      } catch (error) {
        content.innerHTML = '<div class="empty-state"><strong>Não foi possível consultar os resultados</strong><p>Confira a corrida selecionada e tente novamente.</p></div>';
        toast(error.message, true);
      }
    });
  }

  function formatDuration(value) {
    const seconds = Number(value);
    if (!Number.isFinite(seconds)) return safeText(value);
    const total = Math.max(0, Math.round(seconds));
    const hours = Math.floor(total / 3600);
    const minutes = Math.floor((total % 3600) / 60);
    const remaining = total % 60;
    return hours ? `${hours}:${String(minutes).padStart(2, "0")}:${String(remaining).padStart(2, "0")}` : `${minutes}:${String(remaining).padStart(2, "0")}`;
  }

  function renderResults(result) {
    const groups = [
      { title: "Amadores", items: Array.isArray(result?.amadores) ? result.amadores : [] },
      { title: "Elite", items: Array.isArray(result?.elite) ? result.elite : [] }
    ];
    if (!groups.some(group => group.items.length)) {
      return '<div class="empty-state"><span class="empty-illustration">⌁</span><strong>Sem resultados registrados</strong><p>Ainda não há tempos de chegada para esta corrida.</p></div>';
    }
    return `<div class="results-groups">${groups.map(group => `
      <section class="result-group">
        <h3>${escapeHtml(group.title)} <span>(${group.items.length})</span></h3>
        ${group.items.length ? group.items.map((runner, index) => `
          <div class="result-row">
            <span class="position-badge">${index + 1}</span>
            <span class="result-name">${escapeHtml(runner.nome || `Corredor #${runner.numeroPeito}`)}</span>
            <span class="result-time">${escapeHtml(formatDuration(runner.duracaoCorrida))}</span>
          </div>`).join("") : '<div class="empty-cell">Sem participantes nesta categoria.</div>'}
      </section>`).join("")}</div>`;
  }

  function wireNavigation() {
    $$("[data-go]").forEach(element => element.addEventListener("click", event => {
      event.preventDefault();
      window.location.hash = element.dataset.go;
    }));
    window.addEventListener("hashchange", showPage);
  }

  function wireSettings() {
    const dialog = $("#settings-dialog");
    $("#open-settings").addEventListener("click", () => {
      $("#api-base-input").value = apiBase === defaultBase ? "" : apiBase;
      dialog.showModal();
    });
    $("#settings-form").addEventListener("submit", event => {
      if (event.submitter?.id !== "save-settings") return;
      event.preventDefault();
      const value = $("#api-base-input").value.trim().replace(/\/+$/, "");
      apiBase = value || defaultBase;
      if (value) localStorage.setItem("pace-api-base", value);
      else localStorage.removeItem("pace-api-base");
      dialog.close();
      setApiStatus("checking", "Conectando à API");
      Promise.all([loadRaces(), loadRunners()]);
    });
  }

  function init() {
    $("#today-label").textContent = formatDate(new Date(), { weekday: "long", day: "2-digit", month: "long" });
    $("#footer-year").textContent = new Date().getFullYear();
    $("#refresh-races").addEventListener("click", loadRaces);
    $("#refresh-runners").addEventListener("click", loadRunners);
    wireForms();
    wireNavigation();
    wireSettings();
    showPage();
    Promise.all([loadRaces(), loadRunners()]);
  }

  init();
})();
