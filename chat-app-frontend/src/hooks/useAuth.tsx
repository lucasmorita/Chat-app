import { useState } from "react";
import { User } from "../types/User";

type Auth = {
    username: string;
    authed: boolean;
    login: (user: User) => Promise<boolean>;
    logout: () => void;
};

const useAuth = (): Auth => {
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
    
    return { username, authed, login, logout };
};

export default useAuth;