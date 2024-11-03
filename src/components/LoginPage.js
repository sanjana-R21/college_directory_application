import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import {jwtDecode} from 'jwt-decode';
import Navbar from './navbar'; // Import Navbar component
import './LoginPage.css'; // Login page specific styling

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [selectedRole, setSelectedRole] = useState('STUDENT');  
    const navigate = useNavigate();

    const handleLogin = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/auth/login', {
                username,
                password,
            });
            const token = response.data.token;
            localStorage.setItem('token', token);
            const decodedToken = jwtDecode(token);
            const userRole = decodedToken.role || selectedRole;
            const userId = decodedToken.userId || response.data.userId;
            localStorage.setItem('role', selectedRole);
            localStorage.setItem('userId', userId);

            if (userRole === 'STUDENT') {
                navigate('/student/dashboard');
            } else if (userRole === 'FACULTY_MEMBER') {
                navigate('/faculty/dashboard');
            } else if (userRole === 'ADMINISTRATOR') {
                navigate('/admin/dashboard');
            }
        } catch (error) {
            console.error('Login failed:', error);
        }
    };

    return (
        <div>
            <Navbar /> {/* Include the Navbar */}
            <div className="login-container">
                <h1>Login</h1>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="input-box"
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="input-box"
                />
                <select
                    value={selectedRole}
                    onChange={(e) => setSelectedRole(e.target.value)}
                    className="select-box"
                >
                    <option value="STUDENT">Student</option>
                    <option value="FACULTY_MEMBER">Faculty Member</option>
                    <option value="ADMINISTRATOR">Administrator</option>
                </select>
                <button onClick={handleLogin} className="login-button">Login</button>
            </div>
        </div>
    );
};

export default LoginPage;
