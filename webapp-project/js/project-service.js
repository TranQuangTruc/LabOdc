
const API_URL = "http://localhost:8080/api/v1/ideas";

async function fetchApprovedProjects() {
    try {
        const res = await fetch(`${API_URL}/approved`);
        if (!res.ok) throw new Error("Không thể tải dữ liệu");
        return await res.json();
    } catch (error) {
        console.error("Lỗi fetchApprovedProjects:", error);
        return [];
    }
}

async function requestChangeOrCancel(id, type, reason) {
    try {
        const res = await fetch(`${API_URL}/${id}/request-change`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ type, reason })
        });
        return await res.json();
    } catch (error) {
        console.error("Lỗi requestChangeOrCancel:", error);
    }
}