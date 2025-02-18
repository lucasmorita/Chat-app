import { createContext } from "react";
import { User } from "../../types/User";

type AuthContextType = {
    authed: boolean;
    username: string;
    login: (user: User) => Promise<boolean>;
    logout: () => void;
}

export const AuthContext = createContext<AuthContextType>({
    authed: false,
    username: "",
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    login: async (_: User) => false,
    logout: () => {},
});
