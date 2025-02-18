import { useNavigate } from "react-router";
import { User } from "../../types/User";
import { useContext } from "react";
import { AuthContext } from "../../components/context/AuthContext";
import toast, { Toaster } from "react-hot-toast";

const Login = () => {
    const navigate = useNavigate();
    const { login } = useContext(AuthContext);
    const handleLoginSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        const form = e.currentTarget;
        const user: User = {
            username: (form[0] as HTMLInputElement).value,
            password: (form[1] as HTMLInputElement).value,
        };

        console.log(user);
        if (await login(user)) {
            toast.success("Successfully logged in!");
            navigate("/rooms");
        }
    }
    return (
        <><Toaster /><div className="my-auto">
            <h1 className="text-2xl text-center mb-4">Login</h1><div className="m-auto max-w-96 ">
                <form className="flex flex-col gap-4 align-center justify-center" action={`${import.meta.env.VITE_SERVER_HOST}/login`} method="POST" onSubmit={handleLoginSubmit}>
                    <label htmlFor="username" className="floating-label">
                        <span>Username</span>
                        <input name="username" type="text" placeholder="Username" className="input w-full" />
                    </label>
                    <label htmlFor="password" className="floating-label">
                        <span>Password</span>
                        <input name="password" type="password" placeholder="Password" className="input w-full" />
                    </label>
                    <button type="submit" className="btn btn-primary">Login</button>
                </form>
                <div className="text-center mt-4">
                    <span>Don't have an account? </span>
                    <a onClick={() => navigate("/signup")} className="link">Signup</a>
                </div>
            </div>
        </div></>
    )
}

export default Login;