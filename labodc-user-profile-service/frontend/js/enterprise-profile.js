function loadEnterprise() {
    fetch(API_BASE + "/api/enterprise/profile")
        .then(res => res.json())
        .then(data => {
            companyName.value = data.companyName || "";
            companyEmail.value = data.companyEmail || "";
            phone.value = data.phone || "";
            website.value = data.website || "";
            result.innerText = JSON.stringify(data, null, 2);
        })
        .catch(() => {
            result.innerText = "Chua co profile";
        });
}

function saveEnterprise(e) {
    e.preventDefault();

    fetch(API_BASE + "/api/enterprise/profile", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            companyName: companyName.value,
            companyEmail: companyEmail.value,
            phone: phone.value,
            website: website.value
        })
    })
    .then(() => {
        alert("Luu enterprise thanh cong");
        loadEnterprise();
    });
}

loadEnterprise();
