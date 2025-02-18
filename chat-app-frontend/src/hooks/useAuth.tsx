import { useState } from "react";
import { User } from "../types/User";
import { AuthContextType } from "../types/AuthContextType";
import { SignupResponseType } from "../types/SignupResponseType";

const useAuth = (): AuthContextType => {
    const [authed, setAuthed] = useState(false);
    const [username, setUsername] = useState<string>("");

    const login = async (user: User) => {
        const res = await fetch(`${import.meta.env.VITE_SERVER_HOST}/login`, {
            method: "POST",
            headers: { 
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams({
                username: user.username,
                password: user.password,
            }).toString(),
            credentials: 'include',
        });
        if (!res.ok) {
            console.error(res);
            console.error("Failed to login");
            return false;
        }
        setAuthed(true);
        setUsername(user.username);
        return true;
    };
    
    const logout = () => {
        fetch(`${import.meta.env.VITE_SERVER_HOST}/logout`, {
            method: "POST",
            credentials: 'include',
        }).then((res) => {
            if (!res.ok) {
                console.error("Failed to logout");
                return;
            }
            setAuthed(false);
        });
        setUsername("");
    };

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
    
    return { username, authed, login, logout, signup };
};

export default useAuth;