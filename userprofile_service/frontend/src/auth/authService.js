import api from "../api/axios";

export const login = async (email, password) => {
  const res = await api.post("/api/auth/login", {
    email,
    password,
  });

  localStorage.setItem("token", res.data.token);
  localStorage.setItem("userId", res.data.userId);
  localStorage.setItem("role", res.data.role);
};
