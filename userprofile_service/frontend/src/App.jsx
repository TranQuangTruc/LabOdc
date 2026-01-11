import { useState } from "react";
import Login from "./pages/Login";
import TalentProfile from "./pages/TalentProfile";

function App() {
  const [loggedIn, setLoggedIn] = useState(!!localStorage.getItem("token"));

  const logout = () => {
    localStorage.clear();
    setLoggedIn(false);
  };

  return loggedIn
    ? <TalentProfile onLogout={logout} />
    : <Login onLogin={() => setLoggedIn(true)} />;
}

export default App;







