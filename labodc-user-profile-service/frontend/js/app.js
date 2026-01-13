const API_URL = "http://localhost:8080";

/*
  JWT lay tu Auth Service (phan cua Toan)
  Khi demo: dan token vao day
*/
const TOKEN = "PASTE_JWT_TOKEN_HERE";

/* =========================
   ENTERPRISE PROFILE
   ========================= */

function updateEnterpriseProfile() {
    fetch(API_URL + "/api/enterprise/profile", {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + TOKEN
        },
        body: JSON.stringify({
            companyName: document.getElementById("companyName").value,
            taxCode: document.getElementById("taxCode").value,
            website: document.getElementById("website").value,
            description: document.getElementById("description").value
        })
    })
    .then(res => {
        if (!res.ok) throw new Error("Cap nhat that bai");
        return res.json();
    })
    .then(() => {
        document.getElementById("message").innerText =
            "Cap nhat enterprise profile thanh cong";
    })
    .catch(err => {
        document.getElementById("message").innerText = err.message;
    });
}

/* =========================
   TALENT PROFILE
   ========================= */

function loadTalentProfile() {
    fetch(API_URL + "/api/profile/me/talent", {
        headers: {
            "Authorization": "Bearer " + TOKEN
        }
    })
    .then(res => res.json())
    .then(data => {
        document.getElementById("name").value = data.name || "";
        document.getElementById("skills").value = data.skills || "";
        document.getElementById("portfolio").value = data.portfolio || "";
    });
}

/* =========================
   NOTIFICATIONS
   ========================= */

function loadNotifications() {
    fetch(API_URL + "/api/notifications", {
        headers: {
            "Authorization": "Bearer " + TOKEN
        }
    })
    .then(res => res.json())
    .then(data => {
        const list = document.getElementById("notificationList");
        list.innerHTML = "";

        data.forEach(n => {
            const div = document.createElement("div");
            div.className = "card";
            div.innerText = n.title + " - " + n.content;
            list.appendChild(div);
        });
    });
}
