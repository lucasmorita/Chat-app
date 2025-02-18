import React, { useContext } from "react";
import { Navigate } from "react-router";
import { AuthContext } from "./context/AuthContext";


const PrivateRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const { authed } = useContext(AuthContext);
    console.log("private route:", authed);
    if (!authed) {
        return <Navigate to="/login" />;
    }
    return children;
}

export default PrivateRoute;