import { AuthContext } from "./AuthContext";
import useAuth from "../../hooks/useAuth";

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const {
        authed,
        username,
        login,
        logout,
    } = useAuth();
    return (
        <AuthContext.Provider value={{ authed, username, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}