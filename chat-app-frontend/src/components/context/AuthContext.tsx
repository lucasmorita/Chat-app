/* eslint-disable @typescript-eslint/no-unused-vars */
import { createContext } from "react";
import { User } from "../../types/User";
import { AuthContextType } from "../../types/AuthContextType";

export const AuthContext = createContext<AuthContextType>({
    authed: false,
    username: "",
    login: async (_: User) => false,
    signup: async (_: User) => ({ success: false, status: -1 }),
    logout: () => {},
});
