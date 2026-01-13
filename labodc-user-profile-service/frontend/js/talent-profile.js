const API_URL = "http://localhost:8080/api/profile/me/talent";

// Khi mở trang thì tự load profile
document.addEventListener("DOMContentLoaded", () => {
    loadProfile();
});

// ===== LOAD PROFILE (KIEM TRA CO DATA TRONG DATABASE KHONG) =====
async function loadProfile() {
    const token = localStorage.getItem("token");

    if (!token) {
        alert("Ban chua dang nhap");
        return;
    }

    const res = await fetch(API_URL, {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token
        }
    });

    if (res.ok) {
        const data = await res.json();

        document.getElementById("current").innerText =
            JSON.stringify(data, null, 2);

        // do data len form neu da co
        document.getElementById("fullName").value = data.fullName || "";
        document.getElementById("email").value = data.email || "";
        document.getElementById("skills").value = data.skills || "";
        document.getElementById("portfolioUrl").value = data.portfolioUrl || "";
    } else {
        document.getElementById("current").innerText =
            "Chua co profile, vui long tao moi";
    }
}

// ===== SAVE / UPDATE PROFILE =====
async function saveProfile() {
    const token = localStorage.getItem("token");

    if (!token) {
        alert("Ban chua dang nhap");
        return;
    }

    const payload = {
        fullName: document.getElementById("fullName").value,
        email: document.getElementById("email").value,
        skills: document.getElementById("skills").value,
        portfolioUrl: document.getElementById("portfolioUrl").value
    };

    const res = await fetch(API_URL, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(payload)
    });

    if (res.ok) {
        alert("Luu profile thanh cong");
        loadProfile();
    } else {
        alert("Loi khi luu profile");
    }
}
