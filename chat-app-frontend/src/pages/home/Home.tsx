import { useNavigate } from "react-router";
import { useAuth } from "../../components/context/AuthContext";

const Home: React.FC = () => {
  const { authed } = useAuth();
  const navigate = useNavigate();
  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <h1 className="text-3xl">Chat with people using rooms!</h1>
      {
        authed ? <p>
          <button className="btn btn-accent my-8" onClick={() => navigate("/rooms")}>See the open rooms</button>
        </p>: (
          <div className="flex flex-col items-center justify-center">
            <button onClick={() => navigate("/login")} className="btn btn-primary my-2">Login</button>
            <button onClick={() => navigate("/signup")} className="btn btn-accent my-2">Signup</button>
          </div>
        )
      }
    </div>
  );
}

export default Home;