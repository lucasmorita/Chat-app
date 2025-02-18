import { useState } from "react";

const Signup = () => {
    const [password, setPassword] = useState("");
    const [username, setUsername] = useState("");
    const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    }
    const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
    }
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const data = {
            username: username,
            password: password,
        };
        console.log(data);
        const res = await fetch(`${import.meta.env.VITE_SERVER_HOST}/users`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });
        if (!res.ok) {
            if (res.status === 409) {
                alert("Username already exists");
                return;
            }
            console.error("Failed to signup");
            return;
        }
        alert("Signup successful");
        window.location.href = "/login";
    };
    return (
        <><h1 className="text-2xl text-center mb-4">Signup</h1><div className="m-auto w-96">
            <form onSubmit={handleSubmit} action={`${import.meta.env.VITE_SERVER_HOST}/users`} method="POST" className="flex flex-col gap-4 align-center justify-center">
                <label htmlFor="username" className="floating-label">
                    <span>Username</span>
                    <input name="username" type="text" placeholder="Username" value={username} onChange={handleUsernameChange} className="input w-full" />
                </label>
                <label htmlFor="password" className="floating-label">
                    <span>Password</span>
                    <input type="password" name="password" placeholder="Password" value={password} onChange={handlePasswordChange} className="input w-full" />
                </label>
                <button type="submit" className="btn btn-primary">Signup</button>
            </form>
        </div></>
    );
}

export default Signup;