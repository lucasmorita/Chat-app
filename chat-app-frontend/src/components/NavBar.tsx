import { useAuth } from "./context/AuthContext";

const NavBar: React.FC = () => {
    const { authed, loading, logout } = useAuth();
    
    const handleLogout = () => { logout(); }
    return (
        <header>
            <nav>
                <div className="navbar w-full bg-neutral text-neutral-content m-auto">
                    <ul className="flex w-full items-center menu menu-horizontal px-1">
                        <li><a href="/">Home</a></li>
                        <li><a href="/rooms">Rooms</a></li>
                        {
                            (!authed && !loading) ? (
                                <div className="flex">
                                    <li><a href="/signup">Signup</a></li>
                                    <li><a href="/login">Login</a></li>
                                </div>
                            ) : (<>
                                <li className="ml-auto">
                                    <div className="dropdown dropdown-end">
                                        <div tabIndex={0} role="button" className="btn btn-ghost btn-circle avatar">
                                            <div className="w-10 rounded-full">
                                                <img
                                                    alt="Tailwind CSS Navbar component"
                                                    src="/src/assets/images.png" />
                                            </div>
                                        </div>
                                        <ul
                                            tabIndex={0}
                                            className="menu menu-sm dropdown-content bg-base-200 rounded-box z-[1] mt-6 w-52 p-2 shadow">
                                            <li>
                                                <a className="justify-between text-neutral">
                                                    Profile
                                                </a>
                                            </li>
                                            <li><a className="text-neutral">Settings</a></li>
                                            <li><a className="text-neutral" onClick={() => handleLogout()}>Logout</a></li>
                                        </ul>
                                    </div>
                                </li>
                            </>
                            )
                        }

                    </ul>
                </div>
            </nav>
        </header>
    );
};

export default NavBar;