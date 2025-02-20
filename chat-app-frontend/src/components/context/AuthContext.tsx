/* eslint-disable @typescript-eslint/no-unused-vars */
import { createContext, useContext } from "react";
import { SignupResponseType } from "../../types/SignupResponseType";
import { User } from "../../types/User";

export type AuthContextType = {
    authed: boolean;
    username: string;
    login: (user: User) => Promise<boolean>;
    signup: (user: User) => Promise<SignupResponseType>;
    logout: () => void;
    checkSession: () => Promise<void>;
    loading: boolean;
}


export const AuthContext = createContext<AuthContextType>({
    authed: false,
    username: "",
    login: async (_: User) => false,
    signup: async (_: User) => ({ success: false, status: -1 }),
    logout: () => {},
    checkSession: async () => {},
    loading: true
});

export const useAuth = (): AuthContextType => useContext(AuthContext);