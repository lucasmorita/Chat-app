import { SignupResponseType } from "../../types/SignupResponseType";
import { User } from "../../types/User";
import { useState, useEffect } from "react";
import { AuthContext } from "./AuthContext";


export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [authed, setAuthed] = useState(false);
    const [username, setUsername] = useState<string>("");
    const [loading, setLoading] = useState(true);
    const signup = async (user: User): Promise<SignupResponseType> => {
        const res = await fetch(`${import.meta.env.VITE_SERVER_HOST}/users`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user),
        });
        console.log("signup response:", res);
        if (!res.ok) {
            setAuthed(false);
            return {
                success: false,
                reason: `${res.text}`,
                status: res.status
            }
        }
        setAuthed(true);
        setUsername(user.username);
        
        return { success: true, status: res.status };
    }

    const login = async (user: User) => {
        const res = await fetch(`${import.meta.env.VITE_SERVER_HOST}/api/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({
                username: user.username,
                password: user.password,
            }).toString(),
            credentials: 'include',
        });
        if (!res.ok) {
            console.error(res);
            console.error("Failed to login");
            setLoading(false);
            return false;
        }
        setAuthed(true);
        setUsername(user.username);
        setLoading(false);
        return true;
    };
    
    const logout = () => {
        setLoading(true);
        fetch(`${import.meta.env.VITE_SERVER_HOST}/logout`, {
            method: "DELETE",
            credentials: 'include',
        }).then((res) => {
            if (!res.ok) {
                console.error("Failed to logout");
                return;
            }
            setAuthed(false);
        });
        setLoading(false);
        setUsername("");
    };
    const checkSession = async () => {
        try {
          const res = await fetch(`${import.meta.env.VITE_SERVER_HOST}/api/auth/session`, { credentials: 'include' });
          const data = await res.json();
          if (res.ok) {
            setAuthed(true);
            setUsername(data.name);
          } else {
            setAuthed(false);
          }
        } catch (error) {
          setAuthed(false);
          console.error('Session validation failed', error);
        }
      };
    useEffect(() => {
      checkSession(); // This should validate the cookie/session and update the state.
    }, []);
    return (
        <AuthContext.Provider value={{ authed, username, login, logout, signup, checkSession, loading }}>
            {children}
        </AuthContext.Provider>
    );
}