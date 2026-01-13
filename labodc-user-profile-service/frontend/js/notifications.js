function loadNotifications() {
    fetch(API_BASE + "/api/notifications")
        .then(res => res.json())
        .then(data => {
            const list = document.getElementById("list");
            list.innerHTML = "";

            data.forEach(n => {
                const li = document.createElement("li");
                li.innerText = n.title + " - " + n.content;
                list.appendChild(li);
            });
        })
        .catch(err => {
            alert("Khong goi duoc API");
            console.error(err);
        });
}
