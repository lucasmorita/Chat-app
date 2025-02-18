const NavBar: React.FC = () => {
    return (
        <nav className="navbar">
            <div className="flex-none navbar-center m-auto">
                <ul className="menu menu-horizontal px-1">
                    <li><a href="/">Home</a></li>
                    <li><a href="/signup">Signup</a></li>
                    <li><a href="/login">Login</a></li>
                    <li><a href="/rooms">Rooms</a></li>
                </ul>
            </div>
        </nav>
    );
};

export default NavBar;