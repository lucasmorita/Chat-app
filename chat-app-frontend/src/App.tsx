import { BrowserRouter, Routes, Route } from "react-router";
import Home from "./pages/home/Home";
import Login from "./pages/login/Login";
import './App.css';
import PrivateRoute from "./components/PrivateRoute";
import Signup from "./pages/signup/Signup";
import Room from "./pages/rooms/Room";
import RoomChat from "./pages/rooms/chat/RoomChat";
import NavBar from "./components/NavBar";
import { AuthProvider } from "./components/context/AuthProvider";
import { Toaster } from "react-hot-toast";

const App: React.FC = () => {
    return (
        <AuthProvider>
            <NavBar />
            <Toaster />
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
        </AuthProvider>
    );
};

export default App;