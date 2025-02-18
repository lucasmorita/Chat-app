import { SignupResponseType } from "./SignupResponseType";
import { User } from "./User";

export type AuthContextType = {
    authed: boolean;
    username: string;
    login: (user: User) => Promise<boolean>;
    signup: (user: User) => Promise<SignupResponseType>;
    logout: () => void;
}
