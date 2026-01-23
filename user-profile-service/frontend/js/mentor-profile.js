function loadMentor() {
    fetch(API_BASE + "/api/profile/me/mentor")
        .then(res => res.json())
        .then(data => {
            document.getElementById("result").innerText =
                JSON.stringify(data, null, 2);
        })
        .catch(err => {
            alert("Loi khi tai profile mentor");
            console.error(err);
        });
}
