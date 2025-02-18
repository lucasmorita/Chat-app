import { useContext } from "react";
import { AuthContext } from "../../components/context/AuthContext";
import { useNavigate } from "react-router";

const Home: React.FC = () => {
  const { authed } = useContext(AuthContext);
  const navigate = useNavigate();
  return (
    <div className="flex flex-col items-center justify-center h-screen">
      <h1 className="text-3xl">Chat with people using rooms!</h1>
      {
        authed ? <p>
          <button onClick={() => navigate("/rooms")}>See the open rooms</button>
        </p>: (
          <div className="flex flex-col items-center justify-center">
            <a href="/login" className="my-2">Login</a>
            <a href="/signup" className="my-2">Signup</a>
          </div>
        )
      }
    </div>
  );
}

export default Home;