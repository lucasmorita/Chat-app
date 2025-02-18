const LogoutButton: React.FC = () => {
    const handleClick = () => {
        fetch('http://localhost:8080/logout', {
            method: 'POST',
            credentials: 'include'
        }).then(() => {
            window.location.href = '/';
        });
    };
    return (
        <button onClick={handleClick} className="logout-button">Logout</button>
    );
};

export default LogoutButton;