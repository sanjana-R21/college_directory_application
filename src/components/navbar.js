import React from 'react';
import { Link } from 'react-router-dom';
import './navbar.css'; // Assuming CSS file for styling

const Navbar = () => {
    return (
        <nav className="navbar">
            <div className="logo">
                <img src="/pic1.jpeg" alt="Logo" />
            </div>
            <ul className="nav-links">
                <li><Link to="/student/dashboard">Student Dashboard</Link></li>
                <li><Link to="/faculty/dashboard">Faculty Dashboard</Link></li>
                <li><Link to="/admin/dashboard">Admin Dashboard</Link></li>
                <li><Link to="/login">Login</Link></li>
            </ul>
        </nav>
    );
};

export default Navbar;
