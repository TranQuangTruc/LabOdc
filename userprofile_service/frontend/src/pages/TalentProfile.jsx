import { useEffect, useState } from "react";
import api from "../api/axios";

export default function TalentProfile({ onLogout }) {
  const [profile, setProfile] = useState(null);
  const userId = localStorage.getItem("userId");

  useEffect(() => {
    api.get(`/api/talents/${userId}/profile`)
      .then(res => setProfile(res.data))
      .catch(() => alert("Unauthorized"));
  }, []);

  if (!profile) return <div>Loading...</div>;

  return (
    <div>
      <h2>Talent Profile</h2>
      <p><b>Name:</b> {profile.user.fullName}</p>
      <p><b>Email:</b> {profile.user.email}</p>
      <p><b>Bio:</b> {profile.bio}</p>

      <button onClick={onLogout}>Logout</button>
    </div>
  );
}
