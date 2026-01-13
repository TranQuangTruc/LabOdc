async function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const res = await fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
    });

    if (res.ok) {
        const data = await res.json();
        localStorage.setItem("token", data.token);
        alert("Dang nhap thanh cong");
        window.location.href = "talent-profile.html";
    } else {
        alert("Dang nhap that bai");
    }
}