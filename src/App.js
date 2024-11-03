import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import {jwtDecode} from 'jwt-decode';  // Fix import
import LoginPage from './components/LoginPage';
import StudentDashboard from './components/StudentDashboard';
import FacultyDashboard from './components/FacultyDashboard';
import AdminDashboard from './components/AdminDashboard';

const PrivateRoute = ({ children, roles }) => {
    const token = localStorage.getItem('token');
    
    if (!token) {
        return <Navigate to="/login" />;
    }

    try {
        const decodedToken = jwtDecode(token);
        const userRole = decodedToken.role || localStorage.getItem('role');

        if (roles.includes(userRole)) {
            return children;
        }

        return <Navigate to="/login" />;
    } catch (error) {
        console.error('Token decoding error:', error);
        return <Navigate to="/login" />;
    }
};

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/" element={<Navigate to="/login" />} />
                <Route
                    path="/student/dashboard"
                    element={
                        <PrivateRoute roles={['STUDENT']}>
                            <StudentDashboard />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/faculty/dashboard"
                    element={
                        <PrivateRoute roles={['FACULTY_MEMBER']}>
                            <FacultyDashboard />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/admin/dashboard"
                    element={
                        <PrivateRoute roles={['ADMINISTRATOR']}>
                            <AdminDashboard />
                        </PrivateRoute>
                    }
                />
            </Routes>
        </Router>
    );
};

export default App;
