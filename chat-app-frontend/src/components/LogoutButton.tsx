import { useNavigate } from "react-router";
import { useAuth } from "./context/AuthContext";

const LogoutButton: React.FC = () => {
    const navigate = useNavigate();
    const { logout } = useAuth();
    
    const handleClick = () => {
        logout();
        navigate('/');
    };
    return (
        <button onClick={() => handleClick()} className="logout-button">Logout</button>
    );
};

export default LogoutButton;