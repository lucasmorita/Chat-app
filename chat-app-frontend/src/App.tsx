import { BrowserRouter, Routes, Route } from "react-router";
import { AuthContext } from "./components/context/AuthContext";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import useAuth from "./hooks/useAuth";
import './App.css';
import PrivateRoute from "./components/PrivateRoute";
import Signup from "./pages/signup/Signup";
import Room from "./pages/rooms/Room";
import RoomChat from "./pages/rooms/chat/RoomChat";

const App: React.FC = () => {
    const {
        authed,
        username,
        login,
        logout,
    } = useAuth();
    return (
        <main>
            <AuthContext.Provider value={{ authed, username, login, logout }}>
                <BrowserRouter>
                    <Routes>
                        <Route path="/login" element={<Login />} />
                        <Route path="/signup" element={<Signup />} />
                        <Route path="/" element={<Home />} />
                        <Route path="/rooms" element={
                            <PrivateRoute >
                                <Room />
                            </PrivateRoute>
                        } />
                        <Route path="/rooms/:roomId" element={
                            <PrivateRoute >
                                <RoomChat />
                            </PrivateRoute>
                        } />
                    </Routes>
                </BrowserRouter>
            </AuthContext.Provider>
        </main>
    );
};

export default App;