import { useContext, useState } from "react";
import toast, { Toaster } from "react-hot-toast";
import { useNavigate } from "react-router";
import { AuthContext } from "../../components/context/AuthContext";

const Signup = () => {
    const [password, setPassword] = useState("");
    const [username, setUsername] = useState("");
    const { signup } = useContext(AuthContext);
    const navigate = useNavigate();
    const handlePasswordChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(e.target.value);
    }
    const handleUsernameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
    }
    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const user = {
            username: username,
            password: password,
        };
        const { success, status } = await signup(user);

        if (success) {
            toast.success("Successfully signed up!")
            navigate("/rooms");
            return;
        }
        if (status === 409) {
            toast.error("Username already taken 😢");
            return;
        }
        toast.error("Failed to sign up");
    };
    return (
            <><Toaster /><div className="my-auto">
            <h1 className="text-2xl text-center mb-4">Signup</h1><div className="m-auto w-96">
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
            </div>
        </div></>
    );
}

export default Signup;
